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
        
        State l = new State("agno");
        l.addChild(new State("lorem"));
        l.addChild(new State("Ipsum"));
        State d = l.addChild(new State("dolor"));
        d.addChild(new State("consectetur"));
        
        State k = new State("nbfoas");
        k.addChild(new State("sbgousadfas"));
        k.addChild(new State("Ipsbgaoisum"));
        k.addChild(new State("abusds"));
        k.addChild(new State("abwgoas baoisgb"));
         
        State s = new State("beni");
        
        State iuctus = new State("iuctus");
        
        System.out.println(
        SvgComposer.composeFromRootStates(new State[] { l , s, k, iuctus, new State("terra")},
                                          Arrays.asList(new Transition(null, l, true), new Transition(s,iuctus,false), new Transition(null, d, true))
                ).toString()
        );
    }
    
}
