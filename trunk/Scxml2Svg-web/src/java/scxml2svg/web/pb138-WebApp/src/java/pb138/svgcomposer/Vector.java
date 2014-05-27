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
        if (x==0)
            return (y<0) ? (Math.PI/2) : (Math.PI*3/2);
        if (Math.round(y*100)==0)
            return (x<0) ? 0: (Math.PI);
        double rads = Math.atan(y/x) + Math.PI;
        return rads;
    }
    
    private double x, y;
}
