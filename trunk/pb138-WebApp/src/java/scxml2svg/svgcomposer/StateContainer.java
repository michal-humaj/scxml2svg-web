package scxml2svg.svgcomposer;

import org.w3c.dom.*;

import java.util.List;

import scxml2svg.scxmlmodel.State; 
import scxml2svg.scxmlmodel.FinalState;
import scxml2svg.scxmlmodel.HistoryState;
import scxml2svg.scxmlmodel.ParallelState;
import scxml2svg.scxmlmodel.Transition;

/**
 * Layout that represents a state.
 * @author peping
 */
public class StateContainer extends StateLayout {
    public StateContainer(State state, List<Transition> transitions)
    {
        super((State[]) state.getChildren().toArray(new State[0]), transitions);
        
        thisState = state;
    }
    
    /**
     * Represent this State as a Node.
     * 
     * @param doc Document where to create nodes
     * @return Node representing the State
     */
    @Override public Node toNode(Document doc)
    {
        if (doc == null) return null;
        
        Element group = doc.createElement("g");
        group.setAttribute("class", "state-group");
        
        group.setAttribute("transform", "translate("+(getX()-width/2)+","+(getY()-height/2)+")");
        
        // Add another rectangle if this state is final    
        if (thisState instanceof FinalState)
        {
            Element finalRect = doc.createElement("rect");
            finalRect.setAttribute("x", Double.toString(-2));
            finalRect.setAttribute("width", Double.toString(width+4));
            finalRect.setAttribute("y", Double.toString(-2));
            finalRect.setAttribute("height", Double.toString(height+4));
            finalRect.setAttribute("class", "final-rect");
            group.appendChild(finalRect);
        }
        
        Element rect;
        {
            double w = width, h = height;
            String className = "state-rect";
            if (thisState instanceof FinalState)
                className = "final-rect";
            else if (thisState instanceof ParallelState)
                className = "parallel-rect";
            else if (thisState instanceof HistoryState)
                className = "history-rect";
                       
            
            rect = doc.createElement("rect");
            rect.setAttribute("x", Double.toString(0));
            rect.setAttribute("width", Double.toString(w));
            rect.setAttribute("y", Double.toString(0));
            rect.setAttribute("height", Double.toString(h));
            rect.setAttribute("class", className);
        }
        group.appendChild(rect);
        
        Element layoutElement = doc.createElement("g");
        layoutElement.setAttribute("transform", "translate("+(getWidth()/2)+","+((height+10)/2)+")");
        for (StateContainer c : stateContainers)
        {
            layoutElement.appendChild(c.toNode(doc));
        }
        
        for (TransitionArrow c : transitionArrows)
        {
            layoutElement.appendChild(c.toNode(doc));
        }
        group.appendChild(layoutElement);
        
        
        Element idText;
        {
            idText = doc.createElement("text");
            Size size = TextUtils.getTextSize(thisState.getId(),true,14);
            idText.setAttribute("width", Double.toString(size.getWidth()));
            idText.setAttribute("height", Double.toString(size.getHeight()));
            idText.setAttribute("class", "id-caption");
            idText.setAttribute("x", Double.toString(padding));
            idText.setAttribute("y", Double.toString(size.getHeight()));
            
            Node text = doc.createTextNode(thisState.getId());
            idText.appendChild(text);
        }
        group.appendChild(idText);
        return group;
    }
    
    /**
     * Extends StateLayout's layout and adds deals with the caption's dimensions.
     */
    @Override public void layout()
    {
        super.layout();
        Size size = TextUtils.getTextSize(thisState.getId(),true,14);
        
        width = Math.max(width, size.getWidth()+padding*2);
        height += size.getHeight() + padding;
    }
    
    /**
     * Returns the SCXMLModel state this layout represents.
     * @return state
     */
    public State getState()
    {
        return thisState;
    }
    
    private State thisState;
    private double padding = 5;
}
