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
public class Collide {
    /**
     * Check if 2 boxes with origins in their center collide
     * @param x1
     * @param y1
     * @param width1
     * @param height1
     * @param x2
     * @param y2
     * @param width2
     * @param height2
     * @return 
     */
    static public boolean Boxes(double x1,double y1,double width1,double height1, 
                                double x2,double y2,double width2,double height2)
    {
        double hwidth1 = width1 / 2, hheight1 = height1 / 2; //half of first's dimensions
        double hwidth2 = width2 / 2, hheight2 = height2 / 2; //half of seconds's dimensions
        
        double left1 = x1 - hwidth1, top1 = y1 - hheight1,
               right1 = x1 + hwidth1, bottom1 = y1 + hheight1;
        
        double left2 = x2 - hwidth2, top2 = y2 - hheight2,
               right2 = x2 + hwidth2, bottom2 = y2 + hheight2;
        
        return left1 < right2 && top1 < bottom2 && right1 > left2 && bottom1 > top2;
    }
}
