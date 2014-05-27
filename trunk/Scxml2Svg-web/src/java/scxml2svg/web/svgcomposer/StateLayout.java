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
            for (Transition t: transitions)
            {
                if (t.toState()==states[i])
                    this.transitions.add(t);
            }
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
        
        // bruteforce the radius of a regular polygon, with one child layout
        // as each vertex, in which none of the child layouts collide
        
        double radius = 0;
        boolean collision = true;
        if (stateContainers.length>1)
        for (radius=0; collision ; radius += 2.5)
        {
            collision = false;
            for (int i = 0; i < stateContainers.length; i++)
            {
                double rads1 = 2 * Math.PI / stateContainers.length * i;
                StateLayout cont1 = stateContainers[i];
                
                for (int j = 0; j < stateContainers.length; j++)
                {
                    if (j == i) continue;
                    
                    double rads2 = 2 * Math.PI / stateContainers.length * j;
                    StateLayout cont2 = stateContainers[j];
                    
                    collision |= Collide.Boxes(
                        Math.cos( rads1 ) * radius, - Math.sin( rads1 ) * radius, cont1.getWidth(), cont1.getHeight(),
                        Math.cos( rads2 ) * radius, - Math.sin( rads2 ) * radius, cont2.getWidth(), cont2.getHeight());
                }
            }
        }
        
        // Place the child layouts on the desired coords
        // and compute this layout's width and height
        double minx = -30, maxx = 30, miny = -5, maxy = 5;
        for (int i=0; i < stateContainers.length; i++)        
        {
            StateLayout cont = stateContainers[i];
            double rads = 2 * Math.PI / stateContainers.length * i;
            
            double x = Math.cos(rads) * radius;
            double y =-Math.sin(rads) * radius;
            
            cont.setX(x);
            cont.setY(y);
            
            double hwidth = cont.getWidth() / 2, hheight = cont.getHeight() / 2;
            
            minx = Math.min(minx, x-hwidth); maxx = Math.max(maxx, x+hwidth);
            miny = Math.min(miny, y-hheight); maxy = Math.max(maxy, y+hheight);
        }
        
        // Create the transition arrows
        // Bruteforce them so that they don't collide with states
        Vector[] possibleOrigins =new Vector[] { new Vector(0,-1), new Vector(1,0), new Vector(0,1), new Vector(-1,0) };
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
                for (Vector vec: possibleOrigins)
                {
                    boolean collides = false;
                    
                    for (StateContainer c: stateContainers)
                    {
                        if (c == end) continue;
                        
                        collides |= Collide.Boxes(
                            end.getX()+vec.getX()*(end.getWidth()/2-margin+15),
                            end.getY()+vec.getY()*(end.getHeight()/2-margin+15),
                            Math.abs(vec.getX()*40 + vec.getY()*10),
                            Math.abs(vec.getY()*40 + vec.getX()*10),
                            c.getX(),
                            c.getY(),
                            c.getWidth(),
                            c.getHeight());
                    }
                    
                    if (!collides)
                    {
                        TransitionArrow arr = new TransitionArrow(t);
                        arr.setStart(
                            end.getX()+vec.getX()*40,
                            end.getY()+vec.getY()*40
                        );
                        arr.setEnd(
                            end.getX(),
                            end.getY());
                        
                        transitionArrows.add(arr);
                        break;
                    }
                }
            } else { // t.isInitial()
                StateContainer start;
                
                for (StateContainer c: stateContainers)
                {
                    if (c.getState() == t.fromState())
                        start = c;
                }
                
            }
        }            
        
        width  = Math.max(Math.abs(minx), Math.abs(maxx)) * 2 + margin * 2;
        height = Math.max(Math.abs(miny), Math.abs(maxy)) * 2 + margin * 2;
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
