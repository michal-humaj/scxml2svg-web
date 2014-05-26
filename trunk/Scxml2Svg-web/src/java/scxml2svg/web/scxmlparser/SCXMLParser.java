package scxml2svg.web.scxmlparser;

import scxml2svg.web.scxmlmodel.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SCXMLParser{
	
    /**
     * W3C object model representation of a XML document
     */
	private Document scxmlDoc;
	
	/**
     * Parsed states are stored in here
     */
	private List<State> states;
	
	/**
     * Parsed transitions are stored in here
     */
	private List<Transition> transitions;
	
	/**
     * Default constructor
     */
	public SCXMLParser(){
	}
	
	/**
     * Constructor, calls processing method
	 *
     * @param str	Path to scxml file
     */
	public SCXMLParser(String str){
		process(str);
	}
	
	public State getRootState(){
		return states == null ? null : states.get(0);
	}
	
	public List<State> getAllStates(){
		return Collections.unmodifiableList(states);
	}
	
	public List<Transition> getAllTransitions(){
		return Collections.unmodifiableList(transitions);
	}
	
	//test main
    public static void main(String[] args){
		SCXMLParser scp = new SCXMLParser();
		scp.process("C:\\Users\\Adam\\Desktop\\project\\src\\java\\scxml2svg\\exampleFiles\\example1.scxml");
		scp.printStatesToStdOut();
    }
	
    /**
     * Just for debugging purposes, prints contents of this class to std out
     */
	public void printStatesToStdOut(){
		if(states != null){
			for(State st : states){
				String type = "Standard";
				if(st instanceof FinalState) type = "Final";
				else if(st instanceof ParallelState) type = "Parallel";
				else if(st instanceof HistoryState) type = "History";
				System.out.print(type + " state id: " + st.getId() + ", parent: " + (st.getParent() == null ? " " : st.getParent().getId()) + ", children: ");
				for(State child : st.getChildren()) System.out.print(child.getId() + " ");
				System.out.println();
			}
		}
		
		if(transitions != null){
			for(Transition tr : transitions)
				if(!tr.isInitial()) System.out.println("Transition - from state: " + tr.fromState().getId() + ", to state: " + tr.toState().getId());
			for(Transition tr : transitions)
				if(tr.isInitial()) System.out.println("Initial transition - from state: " + tr.fromState().getId() + ", to state: " + tr.toState().getId());
		}
	}
	
	/**
     * Main processing method, parses scmxml document
	 *
     * @param 	str		Path to scxml file
     */
	public void process(String str){
		// Open document
		try{
			File scxmlFile = new File(str);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			scxmlDoc = db.parse(scxmlFile);
			scxmlDoc.getDocumentElement().normalize();
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		// Call processing method
		process(scxmlDoc);
	}
	
	/**
     * Main processing method, parses scmxml document
	 *
     * @param 	doc		Fully initialized W3C object model representation of a XML document
     */
	public void process(Document doc){
		//if(doc == null) throw IllegalArgumentException("Document is not opened");
		// Reset variable contents
		states = new ArrayList<>();
		transitions = new ArrayList<>();
		
		processElement(doc.getDocumentElement());
		processTransitions(doc.getDocumentElement());
    }
	
	/**
     * Recursive method that should process each element of a document
	 * It is automatically called for each child element of processed element
	 *
     * @param 	e		Element to process
     */
	private void processElement(Element e){
		processElement(e, null);
	}
	/**
     * Recursive method that should process each element of a document
	 * It is automatically called for each child element of processed element
	 *
     * @param 	e		Element to process
	 * @param 	parent	Parent of the element
     */
	private void processElement(Element e, State parent){

		// Load states
        for(Element tmpEl : getChildrenByTagName(e, "state")){
			State tmpParent = addState(tmpEl, parent);
			// Recursively call this method on each child state
			processElement(tmpEl, tmpParent);
		}
		// Load parallel states
        for(Element tmpEl : getChildrenByTagName(e, "parallel")){
			State tmpParent = addState(tmpEl, parent, "parallel");
			// Recursively call this method on each child state
			processElement(tmpEl, tmpParent);
		}
		// Load final states
        for(Element tmpEl : getChildrenByTagName(e, "final")){
			State tmpParent = addState(tmpEl, parent, "final");
			// Recursively call this method on each child state
			processElement(tmpEl, tmpParent);
		}
		// Load history states
        for(Element tmpEl : getChildrenByTagName(e, "history")){
			State tmpParent = addState(tmpEl, parent, "history");
			// Recursively call this method on each child state
			processElement(tmpEl, tmpParent);
		}

		
	}
	
	private void processTransitions(Element e){
		processTransitions(e, null);
	}
	private void processTransitions(Element e, State parent){
		// Load initial transitions
        for(Element tmpEl : getChildrenByTagName(e, "initial")){
			processInitial(tmpEl, e);
		}
		// Load transitions
		for(Element tmpEl : getChildrenByTagName(e, "transition")){
			String fromState = e.getAttribute("id");
			String toState = tmpEl.getAttribute("target");
			State to = getState(toState);
			State from = getState(fromState);
			if(to != null && from != null)
				transitions.add(new Transition(from, to));
		}
		
		// Repeat for states
        for(Element tmpEl : getChildrenByTagName(e, "state")){
			// Recursively call this method on each child state
			processTransitions(tmpEl, getState(tmpEl.getAttribute("id")));
		}
		// Repeat for parallel states
        for(Element tmpEl : getChildrenByTagName(e, "parallel")){
			// Recursively call this method on each child state
			processTransitions(tmpEl, getState(tmpEl.getAttribute("id")));
		}
		// Repeat for final states
        for(Element tmpEl : getChildrenByTagName(e, "final")){
			// Recursively call this method on each child state
			processTransitions(tmpEl, getState(tmpEl.getAttribute("id")));
		}
		// Repeat for history states
        for(Element tmpEl : getChildrenByTagName(e, "history")){
			// Recursively call this method on each child state
			processTransitions(tmpEl, getState(tmpEl.getAttribute("id")));
		}
	}

	
	private void processInitial(Element e, Element parent){
        for(Element tmpEl : getChildrenByTagName(e, "transition")){
			String fromState = parent.getAttribute("id");
			String toState = tmpEl.getAttribute("target");
			State to = getState(toState);
			State from = getState(fromState);
			if(to != null && from != null)
				transitions.add(new Transition(from, to, true));
		}
	}
	
	
	/**
	 * Create new or return old state if it exists
	 *
     * @param 	elID		Element id to save as a state
     */
	private State getState(String elID){
		for(State st : states){
			if(st.getId().equals(elID)) return st;
		}
		return null;
	}

	/**
	 * Creates a new state from element if it does not already exist
	 *
     * @param 	e		Element to save as a state
	 * @param 	parent	Parent of the element
     */
	private State addState(Element e, State parent){
		return addState(e, parent, null);
	}
	/**
	 * Creates a new state from element if it does not already exist
	 *
     * @param 	e			Element to save as a state
	 * @param 	parent		Parent of the element
	 * @param 	type		Type of the state
     */
	private State addState(Element e, State parent, String type){
		String elID = e.getAttribute("id");
		for(State st : states){
			if(st.getId().equals(elID)) return st;
		}
		State newState;
		if(type != null && type.equals("parallel")) newState = new ParallelState(elID, parent);
		else if(type != null && type.equals("final")) newState = new FinalState(elID, parent);
		else if(type != null && type.equals("history")) newState = new HistoryState(elID, parent);
		else newState = new State(elID, parent);
		
		if(newState != null && parent != null){
			parent.addChild(newState);
		}
		
		return (states.add(newState) ? newState : null);
	}
	
	/**
	 * Gets all children of an element by it's tag name
	 *
     * @param 	e		Element to get the children of
	 * @param 	str		Tag name
     */
	private List<Element> getChildrenByTagName(Element e, String str){
		List res = new ArrayList<>();
		Node n;
		NodeList nl = e.getChildNodes();
		for(int i = 0; i < nl.getLength(); ++i){
			n = nl.item(i);
			Element tmpEl = null;
			if(n.getNodeType() == Node.ELEMENT_NODE){
				tmpEl = (Element) n;
			}
			if(tmpEl != null && tmpEl.getTagName().equals(str)) res.add(tmpEl);
		}
		return res;
	}
}
