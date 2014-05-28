package scxml2svg.web.scxmlmodel;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 *  This class represents a state
 */
public class State {
    
    /**
     * Constructor, creates a new state and sets it's id 
     * 
     * @param   id      ID of new state
     */
    public State(String id)
    {
        this(id, null);
    }

    /**
     * Constructor, creates a new state and sets it's id and parent
     * 
     * @param   id      ID of new state
     * @param   parent  Parent of new state
     */
    public State(String id, State parent)
    {
        this.id = id;
	this.parent = parent;
	children = new ArrayList<>();
    }
    
    /**
     * Gets ID of this state
     * 
     * @return  ID
     */
    public String getId() 
    { 
        return id; 
    }
    
    /**
     * Gets the parent state of this state
     * 
     * @return  parent state
     */
    public State getParent() 
    { 
        return parent; 
    }
	
    /**
     * Gets all children of this state
     * 
     * @return  List of children
     */
    public List<State> getChildren() 
    { 
        return Collections.unmodifiableList(children);
    }
    
    /**
     * Adds a child state to this state
     * 
     * @param   State to add as a child
     */
    public State addChild(State state){
	return children.add(state) == true ? state : null;
    }
    
    /**
     * *Internal* Adds a transition to the list of transitions. 
     * Checks if transition actually contains this state and if a transition 
     * from this to the other state already exists.
     * 
     * You won't need to call this method as it is automatically called by 
     * Transition's constructor
     * 
     * @param transition
     * @throws IllegalArgumentException
     * @throws NoSuchElementException 
     */
    public void addTransition(Transition transition)
            throws IllegalArgumentException, NoSuchElementException
    {
        State otherState = null;
        if (transition.fromState().id.equals(id))
            otherState = transition.toState();
        else if (transition.fromState().id.equals(id))
            otherState = transition.fromState();
        else 
            throw new NoSuchElementException("Transition is invalid as it doesn't connect to this State.");

        
        for (Transition tr : transitions)
        {
            if (tr.fromState() == transition.fromState() &&
                tr.toState() == transition.toState())
                throw new IllegalArgumentException("Transition from state \""+id+"\" to \""+otherState.id+"\" already exists!");
        }
        
        transitions.add(transition);
    }
    
    /**
     * Converts this object to string
     *
     * @return  String with description of this object
     */
    @Override
    public String toString(){
        return "State id: " + id + ", parent: " + parent;
    }
    
    protected String id;
    protected State parent;
    protected List<State> children;
    protected List<Transition> transitions;
}
