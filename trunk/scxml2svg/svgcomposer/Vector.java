package scxml2svg.svgcomposer;

/**
 * This class represents an immutable vector.
 * It can be used to pass coordinates or directionatl vectors around.
 * 
 * @author peping
 */
public class Vector {
    /**
     * Constructs an immutable vector.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * X coordiante getter.
     * @return x
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Y coordiante getter
     * @return y
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Returns angle in radians.
     * This method computes the angle in relation to the X axis.
     * It makes sure that the value returned is always between 0 and 2 * PI.
     * @return angle in radians
     */
    public double getRads()
    {
        double pi2= 2 * Math.PI;
        double rads = Math.atan2(-y,-x);
        return (rads % pi2 + pi2) % pi2;
    }
    
    /**
     * Computes the length of the vector.
     * This method uses the Pythagorean theorem to compute the length of 
     * this vector
     * @return length
     */
    public double getLength()
    {
        double length = Math.sqrt(x*x + y*y);
        return length;
    }
    
    private double x, y;
}
