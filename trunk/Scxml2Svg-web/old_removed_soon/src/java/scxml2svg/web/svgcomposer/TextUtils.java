/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scxml2svg.web.svgcomposer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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

