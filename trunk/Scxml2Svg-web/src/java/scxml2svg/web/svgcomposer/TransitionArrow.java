/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.svgcomposer;

import scxml2svg.web.scxmlmodel.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author pepin_000
 */
public class TransitionArrow {
    public TransitionArrow(Transition trans)
    {
        transition = trans;
    }
    
    public Node toNode(Document doc)
    {
        Element line = doc.createElement("line");
        line.setAttribute("class", "transition"+ (transition.isInitial()? " initial": ""));
        line.setAttribute("x1", Double.toString(startX));
        line.setAttribute("y1", Double.toString(startY));
        line.setAttribute("x2", Double.toString(endX));
        line.setAttribute("y2", Double.toString(endY));
                
        return line;
    }
    
    public void setStart(double x, double y)
    {
        startX = x;
        startY = y;
    }
    
    public double getStartX() {return startX;}   
    public double getStartY() {return startY;}
    
    public void setEnd(double x, double y)
    {
        endX = x;
        endY = y;
    }
    
    public double getEndX() {return endX;}   
    public double getEndY() {return endY;}
    
    private Transition transition;
    
    private double startX, startY;
    private double endX, endY;
}
