/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.scxmlmodel;

/**
 *
 * @author Adam
 */
public class ParallelState extends State{
	public ParallelState(String id){
        super(id);
    }
	public ParallelState(String id, State parent){
        super(id, parent);
    }
}
