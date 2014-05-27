/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.svgcomposer;

import java.util.ArrayList;
import scxml2svg.web.scxmlmodel.*;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author pepin_000
 */
public abstract class StateLayout {
    /**
     * State layout manager
     * 
     * @param states
     * @param transitions 
     */
    public StateLayout(State[] states, List<Transition> transitions)
    {
        stateContainers = new StateContainer[states.length];
        transitionArrows = new ArrayList<>();
        this.transitions = new ArrayList<>();
        
        for (int i = 0; i < states.length; i++)
        {
            stateContainers[i] = new StateContainer(states[i],transitions);
        }
         
        for (Transition t: transitions)
        {
            boolean found = false;
            
            for (State s: states)
            {
                if (s == t.toState())
                { found = true; break; }
            }
            
            if (found) this.transitions.add(t);
        }
           
        
        margin = 10;
    }
    
    /**
     * Computes the layout
     */
    public void layout()
    {        
        // layout all child layouts
        for (StateLayout i : stateContainers )
        {
            i.layout();
        }
        
        Bounds bounds = new Bounds();
        bounds.update(-30, -5);
        bounds.update(30, 5);
        double radius = 0;
        
        boolean failure = true;
        if (stateContainers.length>0)
        for (radius=0; failure ; radius += 2.5)
        {
            // bruteforce the radius of a regular polygon, with one child layout
            // as each vertex, in which none of the child layouts collide
            
            failure = false;
            for (int i = 0; i < stateContainers.length; i++)
            {
                double rads1 = 2 * Math.PI / stateContainers.length * i;
                StateLayout cont1 = stateContainers[i];
                
                for (int j = 0; j < stateContainers.length; j++)
                {
                    if (j == i) continue;
                    
                    double rads2 = 2 * Math.PI / stateContainers.length * j;
                    StateLayout cont2 = stateContainers[j];
                    
                    failure |= Collide.Boxes(
                        -Math.cos( rads1 ) * radius, - Math.sin( rads1 ) * radius, cont1.getOuterWidth(), cont1.getOuterHeight(),
                        -Math.cos( rads2 ) * radius, - Math.sin( rads2 ) * radius, cont2.getOuterWidth(), cont2.getOuterHeight());
                }
            }
            if (failure) continue;
            
            // Place the child layouts on the desired coords
            // and compute this layout's width and height

            for (int i=0; i < stateContainers.length; i++)        
            {
                StateLayout cont = stateContainers[i];
                double rads = 2 * Math.PI / stateContainers.length * i;

                double x =-Math.cos(rads) * radius;
                double y =-Math.sin(rads) * radius;

                cont.setX(x);
                cont.setY(y);

                double hwidth = cont.getOuterWidth() / 2, hheight = cont.getOuterHeight() / 2;

                bounds.update(x-hwidth, y-hheight);
                bounds.update(x+hwidth, y+hheight);
            }
            
            transitionArrows = new ArrayList<>();
            
            // Create the transition arrows
            // Bruteforce them so that they don't collide with states
            for (Transition t: transitions)
            {
                StateContainer end = null;
                for (StateContainer c: stateContainers)
                {
                    if (c.getState() == t.toState())
                        end = c;
                }
                if (end == null) continue;

                if (t.isInitial())
                {
                    Vector[] possibleOrigins =new Vector[] { new Vector(0,-1), new Vector(-1,0), new Vector(0,1), new Vector(1,0) };
                    
                    Vector vec = null;
                    for (Vector v : possibleOrigins) {
                        boolean collides = false;
                        for (StateContainer c: stateContainers)
                        {
                            if (c == end) continue;

                            collides |= Collide.Boxes(
                                    end.getX()+v.getX()*(end.getWidth()/2+15),
                                    end.getY()+v.getY()*(end.getHeight()/2+15),
                                    Math.abs(v.getX()*40 + v.getY()*5),
                                    Math.abs(v.getY()*40 + v.getX()*5),
                                    c.getX(),
                                    c.getY(),
                                    c.getWidth(),
                                    c.getHeight());
                        }
                        if (!collides)
                        {
                            vec = v;
                            break;
                        }
                    }
                    
                    if (vec == null) 
                    { 
                        failure = true;
                        break;
                    }
                            
                    TransitionArrow arr = new TransitionArrow(t);

                    double xcor = end.getX()+vec.getX()*(end.width/2+30);
                    double ycor = end.getY()+vec.getY()*(end.height/2+30);

                    bounds.update(xcor+10, ycor+10);
                    bounds.update(xcor-10, ycor-10);

                    arr.setStart(xcor,ycor);

                    arr.setEnd(
                        end.getX()+vec.getX()*(end.width/2),
                        end.getY()+vec.getY()*(end.height/2));

                    transitionArrows.add(arr);

                } else { // t.isInitial()
                    StateContainer start = null;
                    
                    Vector[] possibleOrigins =new Vector[] { new Vector(1,0), new Vector(0,-1), new Vector(-1,0), new Vector(0,1) };

                    for (StateContainer c: stateContainers)
                    {
                        if (c.getState() == t.fromState())
                            start = c;
                    }

                    if (start == null) continue; // wtf?

                    Vector vec1 = possibleOrigins[0];
                    Vector vec2 = possibleOrigins[0];
                    int dir = (int) Math.round(
                            new Vector(end.getX() - start.getX(), end.getY() - start.getY())
                                .getRads() / (Math.PI / 2));
                    boolean cancel = false;
                    for (int i = -1; i < possibleOrigins.length && !cancel; i++)
                    {
                        for (int j = -1; j < possibleOrigins.length && !cancel; j++)
                        {
                            if (i==-1)
                                vec1 = possibleOrigins[dir];
                            else
                                vec1 = possibleOrigins[i];
                            
                            if (j==-1)
                                vec1 = possibleOrigins[(dir+2) % 4];
                            else
                                vec2 = possibleOrigins[j];

                            double x1 = start.getX()+vec1.getX()*(start.width / 2 + 1);
                            double y1 = start.getY()+vec1.getY()*(start.height / 2 + 1);
                            double x2 = end.getX()+vec2.getX()*(end.width / 2 + 1);
                            double y2 = end.getY()+vec2.getY()*(end.height / 2 + 1);

                            boolean collides = false;
                            for (StateContainer cont: stateContainers)
                            {
                                collides |= Collide.LineWithBox(
                                        x1, y1, x2, y2, 
                                        cont.getX(), cont.getY(),
                                        cont.width, cont.height);
                            }
                            if (!collides)
                            {
                                cancel = true;
                                break;
                            }
                        }
                    }
                    
                    if (!cancel) // the loop finished without finding a proper candidate
                    {
                        failure = true;
                        break;
                    }

                    double x1 = start.getX()+vec1.getX()*(start.width / 2 + 1);
                    double y1 = start.getY()+vec1.getY()*(start.height / 2 + 1);
                    double x2 = end.getX()+vec2.getX()*(end.width / 2 + 1);
                    double y2 = end.getY()+vec2.getY()*(end.height / 2 + 1);

                    TransitionArrow arr = new TransitionArrow(t);
                    arr.setStart(x1, y1);
                    arr.setEnd(x2, y2);
                    transitionArrows.add(arr);
                }
            } 
        }           
        
        width  = Math.max(Math.abs(bounds.getMinX()), Math.abs(bounds.getMaxX())) * 2;
        height = Math.max(Math.abs(bounds.getMinY()), Math.abs(bounds.getMaxY())) * 2;
        
    }
    
    /**
     * Node svg representation of the layout
     * 
     * @param doc Document to create elements in. If null, a new document is created
     * @return SVG node
     */
    abstract public Node toNode(Document doc);
    
    /**
     * margin setter
     * 
     * @param distance margin
     */
    public void setMargin(double distance)
    {
        margin = distance;
    }
    
    public double getOuterWidth()
    {
        return width + margin * 2;
    }
    
    public double getOuterHeight()
    {
        return height + margin * 2;
    }
    
    /**
     * width getter
     * @return width
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * height getter
     * @return width
     */
    public double getHeight()
    {
        return height;
    }
    
    /**
     * X getter
     * @return x coordinate of the center of layout
     */
    public double getX()
    {
        return xpos;
    }
    
    /**
     * X setter
     * @param x x coordinate of the center of layout
     */
    public void setX(double x)
    {
        xpos = x;
    }
    
    /**
     * Y getter
     * @return y coordinate of the center of layout
     */
    public double getY()
    {
        return ypos;
    }
    
    /**
     * X setter
     * @param y y coordinate of the center of layout
     */
    public void setY(double y)
    {
        ypos = y;
    }
    
    /**
     * root state containers
     */
    protected StateContainer[] stateContainers;
    
    /**
     * transitions between root states
     */
    private List<Transition> transitions;
    protected List<TransitionArrow> transitionArrows;
    
    /**
     * minimal distance between states
     */
    protected double margin;
    
    protected double width, height;
    
    private double xpos, ypos;
}
