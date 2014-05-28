package scxml2svg.svgcomposer;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;

/**
 * Font and text metrics utility class.
 * 
 * @author peping
 */
public class TextUtils {
    public static final int ACCURACY = 1000;
    public static final String FONT_FAMILY = "Arial";
    
    /**
     * Computes the metrics of given text.
     * This function uses AWT's classes in order to compute a given text's 
     * width and height.
     * 
     * @param text text whose metrics we are to compute
     * @param bold if true, we compute metrics of the text in bold style, rather
     *             than normal
     * @param size size of the font
     * @return size of the given text using font with given parameters
     */
    static public Size getTextSize(String text, boolean bold, int size)
    {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle2D rect = bold ? 
                myBoldFont.getStringBounds(text, frc)
              : myFont.getStringBounds(text, frc);
                
        return new Size((rect.getWidth()+500)/(ACCURACY/size), rect.getHeight()/(ACCURACY/size));
    }
    
    private static final Font myFont = new Font(FONT_FAMILY, Font.PLAIN,(int) ACCURACY);
    private static final Font myBoldFont = new Font(FONT_FAMILY, Font.BOLD,(int) ACCURACY);
}

