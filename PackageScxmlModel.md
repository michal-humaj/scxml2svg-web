# Package ScmlModel #

Package ScmlModel


# Classes #

  * [#Final\_State](#Final_State.md)
  * [#History\_State](#History_State.md)
  * [#Parallel\_State](#Parallel_State.md)
  * [#State](#State.md)
  * [#Transition](#Transition.md)


---


## State ##

_This class represents a state_

**Attributes**
  * protected String id;
  * protected State parent;
  * protected List< State > children;
  * protected List< Transition > transitions;

#### public State(String id) ####

  * Constructor, creates a new state and sets it's id
  * @param id - ID of new state

#### public State(String id, State parent) ####

  * Constructor, creates a new state and sets it's id and parent
  * @param id - ID of new state
  * @param parent - Parent of new state

#### public String getId() ####

  * Gets ID of this state
  * @return  ID

#### public State getParent() ####

  * Gets parent state of this state
  * @return  parent state

#### public List< State > getChildren() ####

  * Gets all children of this state
  * @return  List of children

#### public State addChild(State state) ####

  * Adds a child state to this state
  * @param   State to add as a child

#### public void addTransition(Transition transition) ####

  * **Internal** Adds a transition to the list of transitions.
  * Checks if transition actually contains this state and if a transition
  * from this to the other state already exists.
  * You won't need to call this method as it is automatically called by
  * Transition's constructor
  * @param transition
  * @throws IllegalArgumentException
  * @throws NoSuchElementException


---


## Final State ##

_This class represents a final state_
_(extends State)_

#### public FinalState(String id) ####

  * Constructor, creates a new final state and sets it's id
  * @param id - ID of new state

#### public FinalState(String id, State parent) ####

  * Constructor, creates a new final state and sets it's id and parent
  * @param id - ID of new state
  * @param parent - Parent of new state


---

<div></div>
## History State ##

_This class represents a history state_
_(extends State)_

**Attributes**
  * private String type

#### public HistoryState(String id) ####

  * Constructor, creates a new history state and sets it's id
  * @param id - ID of new state

#### public HistoryState(String id, State parent) ####

  * Constructor, creates a new history state and sets it's id and parent
  * @param id - ID of new state
  * @param parent - Parent of new state

#### public HistoryState(String id, State parent, String type) ####

  * Constructor, creates a new history state and sets it's id, parent and type
  * @param id - ID of new state
  * @param parent - Parent of new state
  * @param type - Type of the state


---


#### Parallel State ####

_This class represents a parallel state_
_(extends State)_

#### public ParallelState(String id) ####

  * Constructor, creates a new parallel state and sets it's id
  * @param id - ID of new state

#### public ParallelState(String id, State parent) ####

  * Constructor, creates a new parallel state and sets it's id and parent
  * @param id - ID of new state
  * @param parent - Parent of new state


---


## Transition ##

_This class represents a transition_

**Attributes**
  * private State to, from;
  * private boolean initial;
  * private String event;
  * private boolean condition;
  * private String type;

#### public Transition(State fromState, State toState) ####

  * Creates a transition between 2 states
  * @param fromState - the state from which the transition originates
  * @param toState - the state in which the transition ends

#### public Transition(State fromState, State toState, boolean initial) ####

  * Creates a transition between 2 states
  * @param fromState - the state from which the transition originates
  * @param toState - the state in which the transition ends
  * @param initial - whether this transition is initial

#### public Transition(State fromState, State toState, String event) ####

  * Creates a transition between 2 states
  * @param fromState - the state from which the transition originates
  * @param toState - the state in which the transition ends
  * @param event - tratnsition event

#### public Transition(State fromState, State toState, String event, boolean initial) ####

  * Creates a transition between 2 states
  * @param fromState - the state from which the transition originates
  * @param toState - the state in which the transition ends
  * @param event - tratnsition event
  * @param initial - whether this transition is initial

#### public State fromState() ####

  * Return the state where the transition originates
  * @return the state from which the transition originates

#### public State toState() ####

  * Return the state where the transition ends
  * @return the state where the transition ends

#### public boolean isInitial() ####

  * Return whether the transition is initial
  * @return  true or false whether the transition is initial

#### public String getEvent() ####

  * Return whether the event for this transition
  * @return  the event for this transition