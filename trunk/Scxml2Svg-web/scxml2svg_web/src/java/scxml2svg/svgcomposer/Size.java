package scxml2svg.svgcomposer;

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
