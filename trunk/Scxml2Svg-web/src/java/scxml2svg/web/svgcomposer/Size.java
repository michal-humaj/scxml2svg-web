/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.svgcomposer;

/**
 *
 * @author pepin_000
 */
public class Size {
    public Size(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getHeight()
    {
        return height;
    }
    
    private double width, height;
}
