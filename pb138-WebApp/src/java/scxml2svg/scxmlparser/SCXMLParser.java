package scxml2svg.scxmlparser;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import scxml2svg.scxmlmodel.State; 
import scxml2svg.scxmlmodel.FinalState;
import scxml2svg.scxmlmodel.HistoryState;
import scxml2svg.scxmlmodel.ParallelState;
import scxml2svg.scxmlmodel.Transition;


/**
 *  SCXMLParser parses SCXML documents and converts them to java objects
 */
public class SCXMLParser{
    
    //example main
    public static void main(String[] args){
        SCXMLParser scp = new SCXMLParser();
        try{
            scp.process("C:\\Users\\Adam\\Documents\\NetBeansProjects\\pb138proj\\build\\classes\\scxml2svg\\exampleFiles\\newTest.scxml");
            //for(State st: scp.getRootStates()) System.out.println(st);
            scp.printStatesToStdOut();
            }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
	
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
     * Constructor, calls processing method on document found at specified path
     *
     * @param str	Path to scxml file
     * @throws IOException, SAXException, ParserConfigurationException
     */
    public SCXMLParser(String str) throws IOException, SAXException, ParserConfigurationException{
        process(str);
    }
    
    /**
     * Returns all states without parents extracted from scxml document
     *
     * @return  all states with no parent
     */
    public List<State> getRootStates(){
        List rootStates = new ArrayList();
        for(State st : states)
            if(st.getParent() == null)
                rootStates.add(st);

        return rootStates;
    }
    
    /**
     * Returns all states extracted from scxml document
     *
     * @return  all states
     */
    public List<State> getAllStates(){
        return Collections.unmodifiableList(states);
    }
    
    /**
     * Returns all Transitions extracted from scxml document
     *
     * @return  all transitions
     */
    public List<Transition> getAllTransitions(){
        return Collections.unmodifiableList(transitions);
    }

    /**
    * For debugging purposes, prints contents of this class to std out
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
				if(tr.isInitial()) System.out.println("Initial transition - from state: " + (tr.fromState() == null ? null : tr.fromState().getId()) + ", to state: " + tr.toState().getId());
		}
	}
	
    /**
    * Main processing method, parses scmxml document
    *
    * @param 	str		Path to scxml file
    * @throws IOException, SAXException, ParserConfigurationException
    */
    public final void process(String str) throws IOException, SAXException, ParserConfigurationException{
		// Open document
		File scxmlFile = new File(str);
                if(scxmlFile == null) throw new IOException("Could not open xml file.");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
                
                // create a SchemaFactory capable of understanding WXS schemas
                //SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                // load a WXS schema, represented by a Schema instance
                //Schema schema = factory.newSchema(new URL("http://www.w3.org/2011/04/SCXML/scxml.xsd"));
                
                dbf.setNamespaceAware(true);
                dbf.setValidating(true);
                dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
                dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
                    "http://www.w3.org/2011/04/SCXML/scxml.xsd");
                
                DocumentBuilder db = dbf.newDocumentBuilder();
                DocumentErrorHandler deh = new DocumentErrorHandler();
                db.setErrorHandler(deh);
                      
                
                // parse
                scxmlDoc = db.parse(scxmlFile);
		scxmlDoc.getDocumentElement().normalize();
		// Call processing method
		process(scxmlDoc);
	}
	
    /**
    * Main processing method, parses scmxml document
    *
    * @param    doc     Fully initialized W3C object model representation of a XML document
    */
    public final void process(Document doc){
		if(doc == null) throw new IllegalArgumentException("Document is not opened");
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
    * @param 	e   Element to process
    */
    private void processElement(Element e){
	processElement(e, null);
    }
    /**
     * Recursive method that should process each element of a document
     * It is automatically called for each child element of processed element
     *
     * @param 	e	Element to process
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
        /**
         * Recursive method that should process each transition from element
         * It is automatically called for each child element of processed element
         *
         * @param 	e	Element to process
         */
	private void processTransitions(Element e){
		processTransitions(e, null);
	}
        /**
         * Recursive method that should process each transition from element
         * It is automatically called for each child element of processed element
         *
         * @param 	e	Element to process
         * @param 	parent	Parent of the element
         */
	private void processTransitions(Element e, State parent){
		// Load initial transitions
        for(Element tmpEl : getChildrenByTagName(e, "initial"))
			processInitial(tmpEl, e);
		// Load transitions
		for(Element tmpEl : getChildrenByTagName(e, "transition")){
			String fromState = e.getAttribute("id");
			String toState = tmpEl.getAttribute("target");
			State to = getState(toState);
			State from = getState(fromState);
                        String event = tmpEl.getAttribute("event");
			if(to != null && from != null)
                            transitions.add(new Transition(from, to, event));
		}
		// Process initial attribute on the state
		String init = e.getAttribute("initial");
		if(init != null && !init.equals("") && getState(init) != null){
			transitions.add(new Transition(getState(e.getAttribute("id")), getState(init), true));
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

        /**
         * Processes each initial transition from element
         *
         * @param 	e	Element to process
         * @param 	parent	Parent of the element
         */
	private void processInitial(Element e, Element parent){
                for(Element tmpEl : getChildrenByTagName(e, "transition")){
			String fromState = parent.getAttribute("id");
			String toState = tmpEl.getAttribute("target");
			State to = getState(toState);
			State from = null;
                        String event = tmpEl.getAttribute("event");
                        //State from = getState(fromState);
			if(to != null) //&& from != null)
				transitions.add(new Transition(from, to, event, true));
		}
	}
		
    /**
     * Return state by its ID if it exists
     *
     * @param 	elID	Element id to save as a state
     * @return          State if it exists or null
     */
    public State getState(String elID){
	for(State st : states){
            if(st.getId().equals(elID)) return st;
	}
	return null;
    }

    /**
     * Creates a new state from element if it does not already exist
     *
     * @param 	e	Element to save as a state
     * @param 	parent  Parent of the element
     * @return          new State
     */
    private State addState(Element e, State parent){
	return addState(e, parent, null);
    }
    
    /**
     * Creates a new state from element if it does not already exist
     *
     * @param 	e	Element to save as a state
     * @param 	parent  Parent of the element
     * @param   type    Type of the state
     * @return          new State or null if it already exists or if new state could not be created
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
		
		if(parent != null){
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
