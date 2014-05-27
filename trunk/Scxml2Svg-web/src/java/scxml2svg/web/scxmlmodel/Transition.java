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
		this.from = fromState;
		this.to = toState;
		this.initial = false;
		this.event = null;
		this.condition = true;
		this.type = null;
    }
	
    /**
     * Creates a transition between 2 states
     * 
     * @param fromState the state from which the transition originates
     * @param toState the state in which the transition ends
	 * @param initial whether this transition is initial
     */
    public Transition(State fromState, State toState, boolean initial)
    {
		this(fromState, toState);
		this.initial = initial;
    }
    /**
     * Creates a transition between 2 states
     * 
     * @param fromState the state from which the transition originates
     * @param toState the state in which the transition ends
	 * @param initial whether this transition is initial
	 * @param event event
	 * @param condition condition
	 * @param type type
     */
    public Transition(State fromState, State toState, String event){
		this(fromState, toState);
		this.initial = false;
		this.event = event;
		this.condition = true;
		this.type = "";
    }
    /**
     * Creates a transition between 2 states
     * 
     * @param fromState the state from which the transition originates
     * @param toState the state in which the transition ends
	 * @param initial whether this transition is initial
	 * @param event event
	 * @param condition condition
	 * @param type type
     */
    public Transition(State fromState, State toState, String event, boolean initial){
		this(fromState, toState);
		this.initial = initial;
		this.event = event;
		this.condition = true;
		this.type = "";
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
	
    public boolean isInitial(){
        return initial;
    }
    public String getEvent(){
        return event;
    }
    public boolean getCondition(){
        return condition;
    }
    public String getType(){
        return type;
    }
    
    public State toState()
    {
        return to;
    }
    
    private State to, from;
	private boolean initial;
	private String event;
	private boolean condition;
	private String type;
}
