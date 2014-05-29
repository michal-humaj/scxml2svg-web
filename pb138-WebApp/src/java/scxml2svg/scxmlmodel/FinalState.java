package scxml2svg.scxmlmodel;

/**
 *  This class represents a final state
 */
public class FinalState extends State{
    /**
     * Constructor, creates a new final state and sets it's id
     * 
     * @param   id      ID of new state
     */
    public FinalState(String id){
        super(id);
    }
    /**
     * Constructor, creates a new final state and sets it's id and parent
     * 
     * @param   id      ID of new state
     * @param   parent  Parent of new state
     */
    public FinalState(String id, State parent){
        super(id, parent);
    }
}
