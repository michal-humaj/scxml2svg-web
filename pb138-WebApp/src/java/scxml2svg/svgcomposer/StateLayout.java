package scxml2svg.svgcomposer;

import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.Document;

import scxml2svg.scxmlmodel.State; 
import scxml2svg.scxmlmodel.Transition;
/**
 * State layout manager
 * @author peping
 */
public abstract class StateLayout {
    /**
     * Prepares a new potential layout.
     * Selects only the transitions that have their endpoint in the input array
     * of states.
     * @param states states to be in this layout
     * @param transitions all transitions available
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
     * Computes the layout.
     */
    public void layout()
    {        
        // layout all child layouts
        for (StateLayout i : stateContainers )
        {
            i.layout();
        }
        
        Bounds bounds = new Bounds();
        bounds.update(-30, -15);
        bounds.update(30, 15);
        double radius = 0;
        
        boolean failure = true;
        if (stateContainers.length>0)
        for (radius=0; failure && radius < 10000000 ; radius += 2.5)
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
                    
                    if (start != end)
                    {
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
                                    vec1 = possibleOrigins[(dir + 4) % 4];
                                else
                                    vec1 = possibleOrigins[i];

                                if (j==-1)
                                    vec1 = possibleOrigins[(dir + 2) % 4];
                                else
                                    vec2 = possibleOrigins[j];

                                double x1 = start.getX()+vec1.getX()*(start.width / 2 + 1);
                                double y1 = start.getY()+vec1.getY()*(start.height / 2 + 1);
                                double x2 = end.getX()+vec2.getX()*(end.width / 2 + 1);
                                double y2 = end.getY()+vec2.getY()*(end.height / 2 + 1);

                                // Shift the arrow a bit so that transition going both ways
                                // between 2 states don't overlap
                                x1 +=  vec1.getY() * 4;
                                y1 +=  vec1.getX() * 4;
                                x2 -=  vec2.getY() * 4;
                                y2 -=  vec2.getX() * 4;

                                boolean collides = false;
                                for (StateContainer cont: stateContainers)
                                {
                                    double w = cont.getOuterWidth(), h = cont.getOuterHeight();
                                    if (cont == start || cont == end)
                                    {
                                        w = cont.width;
                                        h = cont.height;
                                    }
                                    collides |= Collide.LineWithBox(
                                            x1, y1, x2, y2, 
                                            cont.getX(), cont.getY(),
                                            w, h);
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

                        // Shift the arrow a bit so that transition going both ways
                        // between 2 states don't overlap
                        x1 +=  vec1.getY() * 6;
                        y1 +=  vec1.getX() * 6;
                        x2 -=  vec2.getY() * 6;
                        y2 -=  vec2.getX() * 6;

                        TransitionArrow arr = new TransitionArrow(t);

                        if (new Vector(x2-x1, y2-y1).getLength() < arr.getEventTextSize().getWidth()+40)
                        {
                            failure = true;
                            break;
                        }

                        arr.setStart(x1, y1);
                        arr.setEnd(x2, y2);
                        transitionArrows.add(arr);
                    } else {
                        // Transition from one state to the same state
                        
                        TransitionArrow arr = new TransitionArrow(t);
                        Size size = arr.getEventTextSize();
                        
                        double x = end.getX(), y = end.getY();
                        double hw = end.getWidth() / 2, hh = end.getHeight() / 2;
                        double sw = size.getWidth(), sh = size.getHeight();
                        Vector[] origins = new Vector[]{
                            new Vector(x - hw + 20 - sw / 2, y - hh - 20), // top left
                            new Vector(x - hw - 20 - sw, y + hh - 20),     // left bottom
                            new Vector(x + hw - 20 - sw / 2, y + hh + 20 + sh / 2), // bottom right
                            new Vector(x + hw + 20, y - hh + 20),          // right top
                        };
                        Vector[] plainOrigins = new Vector[]{
                            new Vector(x - hw + 20, y - hh - 20), // top left
                            new Vector(x - hw - 20, y + hh - 20),     // left bottom
                            new Vector(x + hw - 20, y + hh + 20),  // bottom right
                            new Vector(x + hw + 20, y - hh + 20),          // right top
                        };
                        double[] angles = new double[] { 90, 0, 270, 180, };
                        
                        int i;
                        for (i=0; i<4; i++)
                        {
                            boolean collides = false;
                            for (StateContainer cont: stateContainers)
                            {
                                if (cont == end) continue;
                                
                                boolean c = Collide.Boxes(
                                    origins[i].getX(), origins[i].getY(), 
                                    origins[i].getX() + size.getWidth(), origins[i].getY() + size.getHeight(),
                                    cont.getX(), cont.getY(), 
                                    cont.getX() + cont.getWidth(), cont.getY() + cont.getHeight());
                                collides |= c;
                            }
                            if (!collides) break;
                        }
                        if (i==4)
                        {
                            failure = true;
                            break;
                        }
                        
                        bounds.update(origins[i].getX() - margin, origins[i].getY() - margin);
                        bounds.update(origins[i].getX() + sw + margin, origins[i].getY() + margin);
                        
                        arr.setStart(plainOrigins[i].getX(), plainOrigins[i].getY());
                        arr.setEnd(angles[i],0);
                        transitionArrows.add(arr);
                    }
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
    
    /**
     * Gets the outer width of this layout.
     * @return outer width
     */
    public double getOuterWidth()
    {
        return width + margin * 2;
    }
    
    /**
     * Gets the outer height of this layout.
     * @return outer height
     */
    public double getOuterHeight()
    {
        return height + margin * 2;
    }
    
    /**
     * Width getter.
     * @return width
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * Height getter.
     * @return height
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
