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
public class Bounds {
    Bounds()
    {
        minx = 0; miny = 0; maxx = 0; maxy = 0;
    }
    
    void update(double x, double y)
    {
        minx = Math.min(minx, x); maxx = Math.max(maxx, x);
        miny = Math.min(miny, y); maxy = Math.max(maxy, y);
    }
    
    double getMinX() { return minx; }
    double getMinY() { return miny; }
    double getMaxX() { return maxx; }
    double getMaxY() { return maxy; }
    
    double minx, miny, maxx, maxy;
}
