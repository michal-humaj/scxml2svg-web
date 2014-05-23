/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.scxmlmodel;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author pepin_000
 */
public class State {
    public State(String id)
    {
        this.id = id;
    }
    
    public String getId() 
    { 
        return id; 
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
    
    private String id;
    
    private List<Transition> transitions;
}
