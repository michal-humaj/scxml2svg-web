package scxml2svg.web.scxmlmodel;

/**
 *  This class represents a prallel state
 */
public class ParallelState extends State{
    /**
     * Constructor, creates a new parallel state and sets it's id
     * 
     * @param   id      ID of new state
     */
    public ParallelState(String id){
        super(id);
    }
    /**
     * Constructor, creates a new prallel state and sets it's id and parent
     * 
     * @param   id      ID of new state
     * @param   parent  Parent of new state
     */
    public ParallelState(String id, State parent){
        super(id, parent);
    }
}
