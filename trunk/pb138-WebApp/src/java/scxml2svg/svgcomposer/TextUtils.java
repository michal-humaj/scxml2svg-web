package scxml2svg.svgcomposer;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;

/**
 *
 * @author pepin_000
 */
public class TextUtils {
    public static final int ACCURACY = 1000;
    public static final String FONT_FAMILY = "Arial";
    
    private static final Font myFont = new Font(FONT_FAMILY, Font.PLAIN,(int) ACCURACY);
    private static final Font myBoldFont = new Font(FONT_FAMILY, Font.BOLD,(int) ACCURACY);
    
    static public Size getTextSize(String text, boolean bold, int size)
    {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle2D rect = bold ? 
                myBoldFont.getStringBounds(text, frc)
              : myFont.getStringBounds(text, frc);
                
        return new Size(rect.getWidth()/(ACCURACY/size)+10, rect.getHeight()/(ACCURACY/size));
    }
}

