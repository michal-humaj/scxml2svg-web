/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.scxmlmodel;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 *
 * @author pepin_000
 */
public class State {
    public State(String id)
    {
        this(id, null);
    }
	
    public State(String id, State parent)
    {
        this.id = id;
		this.parent = parent;
		children = new ArrayList<>();
    }
    
    public String getId() 
    { 
        return id; 
    }
	
    public State getParent() 
    { 
        return parent; 
    }
	
    public List<State> getChildren() 
    { 
        return Collections.unmodifiableList(children);
    }
	
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
    
    @Override
    public String toString(){
        return "State id: " + id + ", parent: " + parent;
    }
    
    protected String id;
    protected State parent;
	protected List<State> children;
    protected List<Transition> transitions;
}
