package scxml2svg.svgcomposer;

/**
 * Two-dimensional immutable size primitive.
 * @author peping
 */
public class Size {
    /**
     * Creates a size with given width and height.
     * @param width
     * @param height 
     */
    public Size(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns the size's width
     * @return width
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * Returns the size's height
     * @return height
     */
    public double getHeight()
    {
        return height;
    }
    
    private double width, height;
}
