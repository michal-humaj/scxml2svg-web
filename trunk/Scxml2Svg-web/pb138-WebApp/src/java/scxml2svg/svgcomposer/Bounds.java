package scxml2svg.svgcomposer;

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
