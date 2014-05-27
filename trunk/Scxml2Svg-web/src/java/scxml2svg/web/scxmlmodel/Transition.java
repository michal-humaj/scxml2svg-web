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
     * @param toState   the state in which the transition ends
     * @param initial   whether this transition is initial
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
     * @param toState   the state in which the transition ends
     * @param event     tratnsition event
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
     */
    public Transition(State fromState, State toState, String event, boolean initial){
		this(fromState, toState);
		this.initial = initial;
		this.event = event;
		this.condition = true;
		this.type = "";
    }
    
    /**
     * Return the state where the transition originates
     * 
     * @return the state from which the transition originates
     */    
    public State fromState()
    {
        return from;
    }
    
    /**
     * Return whether the transition is initial
     * 
     * @return  true or false whether the transition is initial
     */   
    public boolean isInitial(){
        return initial;
    }
    
    /**
     * Return whether the event for this transition
     * 
     * @return  the event for this transition
     */   
    public String getEvent(){
        return event;
    }

    /**
     * Return the state where the transition ends
     * 
     * @return the state where the transition ends
     */   
    public State toState(){
        return to;
    }
    
    private State to, from;
    private boolean initial;
    private String event;
    private boolean condition;
    private String type;
}
