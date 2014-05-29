package scxml2svg.scxmlmodel;

/**
 *  This class represents a history state
 */
public class HistoryState extends State{
    /**
     * Constructor, creates a new history state and sets it's id
     * 
     * @param   id      ID of new state
     */
    public HistoryState(String id){
        super(id);
    }
    /**
     * Constructor, creates a new history state and sets it's id and parent
     * 
     * @param   id      ID of new state
     * @param   parent  Parent of new state
     */
    public HistoryState(String id, State parent){
        super(id, parent);
    }
    /**
     * Constructor, creates a new history state and sets it's id, parent and type
     * 
     * @param   id      ID of new state
     * @param   parent  Parent of new state
     * @param   type    Type of the state
     */
    public HistoryState(String id, State parent, String type){
        super(id, parent);
		this.type = type;
    }
	
    private String type;
}