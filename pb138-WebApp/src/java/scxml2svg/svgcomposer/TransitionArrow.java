package scxml2svg.svgcomposer;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import scxml2svg.scxmlmodel.Transition;

/**
 * Class for SVG TransitionArrow.
 * This class models and generates the arrow, which serves as the visual means
 * of communicating transitions. This class serves merely as a generator for the
 * visualization
 * @author peping
 */
public class TransitionArrow {
    /**
     * Constructs a TransitionArrow with a given Transition.
     * @param trans 
     */
    public TransitionArrow(Transition trans)
    {
        transition = trans;
    }
    
    /**
     * Converts this class to a Node.
     * Generates the appropriate transition arrow at specified coordinates.
     * @param doc Document in which we create elements
     * @return arrow node
     */
    public Node toNode(Document doc)
    {
        Element group = doc.createElement("g");
        group.setAttribute("class", "transition"+ (transition.isInitial()? " initial": ""));
        
        String event_text = transition.getEvent();
        if (event_text == null) event_text = "";
        
        if (transition.isInitial())
        {
            Element circle = doc.createElement("circle");
            circle.setAttribute("cx", Double.toString(startX));
            circle.setAttribute("cy", Double.toString(startY));
            circle.setAttribute("r", "5");
            group.appendChild(circle);
        }
        
        double degs = (new Vector(endX-startX, endY-startY)).getRads()*(180/(Math.PI));
        
        if (transition.fromState() != transition.toState())
        {
            Element line = doc.createElement("line");
            line.setAttribute("x1", Double.toString(startX));
            line.setAttribute("y1", Double.toString(startY));
            line.setAttribute("x2", Double.toString(endX));
            line.setAttribute("y2", Double.toString(endY));
            group.appendChild(line);
            
            // arrow head
            Element head = doc.createElement("polygon");
            head.setAttribute("class","arrow-head");
            head.setAttribute("transform", "translate("+endX+","+endY+") rotate("+(degs+90)+")");
            head.setAttribute("points","0,0 -4,-8 4,-8");
            group.appendChild(head);

            // event name along the line
            Element textGroup = doc.createElement("g");
            textGroup.setAttribute("x", Double.toString((startX + endX) / 2));
            textGroup.setAttribute("y", Double.toString((startY + endY) / 2));
            Element text;
            {
                double angle = degs;
                if (angle > 90 && angle < 270) angle = (angle + 180) % 360;
                Size size = getEventTextSize();

                text = doc.createElement("text");
                text.setAttribute("transform", "translate("+((startX + endX) / 2)+","+((startY + endY) / 2)+") " +
                                               "rotate(" + angle + ") " +
                                               "translate(" + (-size.getWidth() / 2) + ",10)");
                text.setAttribute("width", Double.toString(size.getWidth()));
                text.setAttribute("height", Double.toString(size.getHeight()));
                text.setAttribute("class", "transition-event");

                text.appendChild(doc.createTextNode(event_text));
            }
            textGroup.appendChild(text);
            group.appendChild(textGroup);
        } else {
            Size size = getEventTextSize();
            
            Element arr = doc.createElement("g");
            arr.setAttribute("transform", "translate("+startX+","+startY+")");
            
            Element path = doc.createElement("path");
            path.setAttribute("d", "M-20 16 L-4 16 L-4 -16 L-20 -16");
            path.setAttribute("class","self-transition");
            path.setAttribute("transform", "rotate("+(-endX)+")"); 
            arr.appendChild(path);
            path = doc.createElement("path");
            path.setAttribute("d", "M-20 -16 l8 -4 l0 8 l-8 -4 Z");
            path.setAttribute("transform", "rotate("+(-endX)+")"); 
            arr.appendChild(path);
            
            Vector[] origins = new Vector[] {
                new Vector(0, 0), new Vector(-size.getWidth() / 2, 0),
                new Vector(-size.getWidth(), 0), 
                new Vector(-size.getWidth() / 2, size.getHeight() / 2)
            };
            
            Element text = doc.createElement("text");
            text.setAttribute("width", Double.toString(size.getWidth()));
            text.setAttribute("height", Double.toString(size.getHeight()));
            text.setAttribute("class", "transition-event");
            text.setAttribute("x", Double.toString(origins[(int)endX/90].getX()));
            text.setAttribute("y", Double.toString(origins[(int)endX/90].getY()));
            text.appendChild(doc.createTextNode(event_text));
            arr.appendChild(text);
            
            group.appendChild(arr);
        }
        
        return group;
    }
    
    /**
     * Sets the starting point for the arrow.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setStart(double x, double y)
    {
        startX = x;
        startY = y;
    }
    
    /**
     * Gets the starting X coordinate.
     * @return X
     */
    public double getStartX() {return startX;}   
    
    /**
     * Gets the starting Y coordinate.
     * @return Y
     */
    public double getStartY() {return startY;}
    
    /**
     * Sets the point where the arrow ends.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setEnd(double x, double y)
    {
        endX = x;
        endY = y;
    }
    /**
     * Gets the end's X coordinate.
     * @return X
     */
    public double getEndX() {return endX;} 
    /**
     * Gets the end's Y coordinate.
     * @return Y
     */
    public double getEndY() {return endY;}
    
    /**
     * Returns the size of the text caption carrying the event name.
     * @return size of text
     */
    public Size getEventTextSize()
    {
        String text = transition.getEvent();
        if (text == null) 
            return new Size(0,0);
        
        Size size = TextUtils.getTextSize(text, false, 11);
        return size;
    }
        
    private Transition transition;
    
    private double startX, startY;
    private double endX, endY;
}
