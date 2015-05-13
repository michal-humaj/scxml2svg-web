# Package Sxml Parser #

Scxml Parser


# Classes #

  * [#Document\_Error\_Handler](#Document_Error_Handler.md)
  * [#Scxml\_Parser](#Scxml_Parser.md)


---


## Document Error Handler ##

_Error handler for xml schema validation_
_(implements ErrorHandler)_

#### public void warning(SAXParseException exception) ####

  * Catches a warning from document validation
  * @param exception - exception with warning
  * @throws SAXException

#### public void error(SAXParseException exception) ####

  * Catches a error from document validation
  * @param exception - exception with warning
  * @throws SAXException

#### public void fatalError(SAXParseException exception) ####

  * Catches a fatalError from document validation
  * @param exception - exception with warning
  * @throws SAXException


---


## Scxml Parser ##

_SCXMLParser parses SCXML documents and converts them to java objects_
_Contains example of main_

**Attributes:**
  * private Document scxmlDoc
  * private List< State > states _(Parsed states are stored in here)_
  * private List< Transition > transitions _(Parsed transitions are)_ stored in here

#### public SCXMLParser(String str) ####

  * Constructor, calls processing method on document found at specified path
  * @param str - Path to scxml file
  * @throws IOException, SAXException, ParserConfigurationException


#### public List< State > getRootStates() ####

  * Returns all states without parents extracted from scxml document
  * @return  all states with no parent

#### public List< State > getAllStates() ####

  * Returns all states extracted from scxml document
  * @return  all states

#### public List< Transition > getAllTransitions() ####

  * Returns all Transitions extracted from scxml document
  * @return  all transitions

#### public void printStatesToStdOut() ####

  * For debugging purposes, prints contents of this class to std out

#### public final void process(String str) ####

  * Main processing method, parses scmxml document
  * @param str - Path to scxml file
  * @throws IOException, SAXException, ParserConfigurationException

#### public final void process(Document doc) ####

  * Main processing method, parses scmxml document
  * @param doc - Fully initialized W3C object model representation of a XML document

#### private void processElement(Element e) ####

  * Recursive method that should process each element of a document
  * It is automatically called for each child element of processed element
  * @param e - Element to process

#### private void processElement(Element e, State parent) ####

  * Recursive method that should process each element of a document
  * It is automatically called for each child element of processed element
  * @param e - Element to process
  * @param parent - Parent of the element

#### private void processTransitions(Element e) ####

  * Recursive method that should process each transition from element
  * It is automatically called for each child element of processed element
  * @param e - Element to process

#### private void processTransitions(Element e, State parent) ####

  * Recursive method that should process each transition from element
  * It is automatically called for each child element of processed element
  * @param e - Element to process
  * @param parent - Parent of the element

#### private void processInitial(Element e, Element parent) ####

  * Processes each initial transition from element
  * @param e - Element to process
  * @param parent - Parent of the element

#### public State getState(String elID) ####

  * Return state by its ID if it exists
  * @param elID	- Element id to save as a state
  * @return State if it exists or null

#### private State addState(Element e, State parent) ####

  * Creates a new state from element if it does not already exist
  * @param e - Element to save as a state
  * @param parent - Parent of the element
  * @return new State

#### private State addState(Element e, State parent, String type) ####

  * Creates a new state from element if it does not already exist
  * @param e - Element to save as a state
  * @param parent - Parent of the element
  * @param type - Type of the state
  * @return new State or null if it already exists or if new state could not be created

#### private List< Element > getChildrenByTagName(Element e, String str) ####

  * Gets all children of an element by it's tag name
  * @param e - Element to get the children of
  * @param str - Tag name