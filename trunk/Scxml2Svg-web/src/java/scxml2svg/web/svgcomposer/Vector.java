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
public class Vector {
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getRads()
    {
        return (x!=0) ? ((Math.atan(y/x) + Math.PI) % (Math.PI * 2)) : ( (y<0) ? (Math.PI/2) : (Math.PI*3/2) );
    }
    
    private double x, y;
}
