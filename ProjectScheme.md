# Project Scheme & Design #

## Project Scheme contains multiple important parts: ##
  * SVG Factory
  * SCXML Parser
  * Transition, ParallelState, Operation, History State

## Description & How it works: ##

**SVG Factory** is main class for creating svg files from scxml using **SCXML Parser**. **SCXML Parser** parse scxml files into multiple classes such as _States_, _Transitions_, _Operations_ etc. **Svg Factory** uses all these classes and svg style to create user friendly svg visualization of desired automata.

User interface and svg output will be implemented in web application.

![https://scxml2svg-web.googlecode.com/svn/class%20diagram.jpg](https://scxml2svg-web.googlecode.com/svn/class%20diagram.jpg)