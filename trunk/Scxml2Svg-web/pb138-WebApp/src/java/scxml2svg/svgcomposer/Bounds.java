package scxml2svg.svgcomposer;

/**
 * Bounds class facilitates bounding boxes.
 * @author peping
 */
public class Bounds {
    /**
     * Creates a zero-width bounding box.
     */
    Bounds()
    {
        minx = 0; miny = 0; maxx = 0; maxy = 0;
    }
    
    /**
     * Updates the box with new coordinates.
     * If they're out of current bounds, the rectangle expands to fit them in.
     * @param x
     * @param y 
     */
    void update(double x, double y)
    {
        minx = Math.min(minx, x); maxx = Math.max(maxx, x);
        miny = Math.min(miny, y); maxy = Math.max(maxy, y);
    }
    
    /**
     * Gets the left side boundary
     * @return minx
     */
    double getMinX() { return minx; }
    
    /**
     * Gets the top side boundary
     * @return miny
     */
    double getMinY() { return miny; }
    
    /**
     * Gets the right side boundary
     * @return maxx
     */
    double getMaxX() { return maxx; }
    
    /**
     * Gets the bottom side boundary
     * @return maxy
     */
    double getMaxY() { return maxy; }
    
    /**
     * Computes width
     * @return width
     */
    double getWidth() {
        return maxx - minx;
    }
    
    /**
     * Computes height
     * @return height
     */
    double getHeight() {
        return maxy - miny;
    }
    
    double minx, miny, maxx, maxy;
}
