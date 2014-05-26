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
public class HistoryState extends State{
	public HistoryState(String id){
        super(id);
    }
	public HistoryState(String id, State parent){
        super(id, parent);
    }
	public HistoryState(String id, State parent, String type){
        super(id, parent);
		this.type = type;
    }
	
	private String type;
}