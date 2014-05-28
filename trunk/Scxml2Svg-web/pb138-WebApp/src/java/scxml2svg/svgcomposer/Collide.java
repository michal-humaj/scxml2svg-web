package scxml2svg.svgcomposer;

/**
 * Static utility class for collisions on a 2D plane
 * @author peping
 */
public class Collide {
    /**
     * Check if 2 boxes with origins in their center collide.
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
    static public boolean Boxes(double x1, double y1, double width1, double height1, 
                                double x2, double y2, double width2, double height2)
    {
        double hwidth1 = width1 / 2, hheight1 = height1 / 2; //half of first's dimensions
        double hwidth2 = width2 / 2, hheight2 = height2 / 2; //half of seconds's dimensions
        
        double left1 = x1 - hwidth1, top1 = y1 - hheight1,
               right1 = x1 + hwidth1, bottom1 = y1 + hheight1;
        
        double left2 = x2 - hwidth2, top2 = y2 - hheight2,
               right2 = x2 + hwidth2, bottom2 = y2 + hheight2;
        
        return left1 < right2 && top1 < bottom2 && right1 > left2 && bottom1 > top2;
    }
    
    /**
     * Returns true if line intersects with rectangle.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param boxx
     * @param boxy
     * @param width
     * @param height
     * @return 
     */
    static public boolean LineWithBox(double x1, double y1, double x2, double y2,
                                      double boxx, double boxy, double width, double height)
    {
        return Collide.Lines(x1, y1, x2, y2, boxx - width / 2, boxy - height / 2, boxx - width / 2, boxy + height / 2 ) || // left side 
               Collide.Lines(x1, y1, x2, y2, boxx - width / 2, boxy - height / 2, boxx + width / 2, boxy - height / 2 ) || // top side
               Collide.Lines(x1, y1, x2, y2, boxx + width / 2, boxy - height / 2, boxx + width / 2, boxy + height / 2 ) || // right side
               Collide.Lines(x1, y1, x2, y2, boxx - width / 2, boxy + height / 2, boxx + width / 2, boxy + height / 2 );   // bottom side
    }
    
    /**
     * Returns true if lines intersect.
     * adapted from http://tog.acm.org/resources/GraphicsGems/gemsii/xlines.c
     * @param x1 first line segment's starting x
     * @param y1 first line segment's starting y
     * @param x2 first line segment's ending x
     * @param y2 first line segment's ending y
     * @param x3 second line segment's starting x
     * @param y3 second line segment's starting y
     * @param x4 second line segment's ending x
     * @param y4 second line segment's ending y
     * @return 
     */
    static public boolean Lines(double x1, double y1, double x2, double y2,
                                double x3, double y3, double x4, double y4)
    {
        double a1, a2, b1, b2, c1, c2; /* Coefficients of line eqns. */
        double r1, r2, r3, r4;         /* 'Sign' values */

        /* Compute a1, b1, c1, where line joining points 1 and 2
         * is "a1 x  +  b1 y  +  c1  =  0".
         */

        a1 = y2 - y1;
        b1 = x1 - x2;
        c1 = x2 * y1 - x1 * y2;

        /* Compute r3 and r4.
         */


        r3 = a1 * x3 + b1 * y3 + c1;
        r4 = a1 * x4 + b1 * y4 + c1;

        /* Check signs of r3 and r4.  If both point 3 and point 4 lie on
         * same side of line 1, the line segments do not intersect.
         */

        if ( r3 != 0 &&
             r4 != 0 &&
             Math.signum(r3) == Math.signum(r4) )
            return false;

        /* Compute a2, b2, c2 */

        a2 = y4 - y3;
        b2 = x3 - x4;
        c2 = x4 * y3 - x3 * y4;

        /* Compute r1 and r2 */

        r1 = a2 * x1 + b2 * y1 + c2;
        r2 = a2 * x2 + b2 * y2 + c2;

        /* Check signs of r1 and r2.  If both point 1 and point 2 lie
         * on same side of second line segment, the line segments do
         * not intersect.
         */

        if ( r1 != 0 &&
             r2 != 0 &&
             Math.signum(r1) == Math.signum(r2))
            return false;

        /* Line segments intersect: compute intersection point. 
         */

        return true;
    } 
    
}
