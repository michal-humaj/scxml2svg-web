/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web;

import java.util.ArrayList;
import java.util.Arrays;
import scxml2svg.web.scxmlmodel.State;
import scxml2svg.web.scxmlmodel.Transition;
import scxml2svg.web.scxmlparser.SCXMLParser;
import scxml2svg.web.svgcomposer.SvgComposer;

/**
 *
 * @author pepin_000
 */
public class SvgFactory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        SCXMLParser scp = new SCXMLParser();
        try{
            scp.process("/home/xbrenkus/Documents/pb138repo/scxml2svg-web/Scxml2Svg-web/src/java/scxml2svg/exampleFiles/newTest.scxml");
            //for(State st: scp.getRootStates()) System.out.println(st);
            //scp.printStatesToStdOut();
            }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        System.out.println(
        SvgComposer.composeFromRootStates(scp.getRootStates().toArray(new State[0]), scp.getAllTransitions()
                                          
                ).toString()
        );
    }
    
}
