/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.scxmlmodel;

/**
 *
 * @author peping
 */
public class Transition {
    /**
     * Creates a transition between 2 states
     * 
     * @param fromState the state from which the transition originates
     * @param toState the state in which the transition ends
     */
    public Transition(State fromState, State toState)
    {
    }
    
    /**
     * Removes the transition from both ends
     */
    public void remove()
    {
    }
    
    public State fromState()
    {
        return from;
    }
    
    public State toState()
    {
        return to;
    }
    
    private State to, from;
}
