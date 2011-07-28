package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.internal.Glyph;

public class AwtGlyph implements Glyph {

    public static long total;
    private final int width;
    private final int height;
    private AwtColorConverter converter;
    private final BufferedImage image;
    private final Map<ColourPair, Image> map = new HashMap<ColourPair, Image>();
    private BufferedImage alphabet;
    private int x;
    private int y;

    //    public AwtGlyph(AwtColorConverter converter, BufferedImage image) {
    //        this.converter = converter;
    //        this.image = image;
    //        width = image.getHeight(null);
    //        height = image.getWidth(null);
    //    }

    public AwtGlyph(AwtColorConverter converter, BufferedImage alphabet, Rectangle clip) {
        this.converter = converter;
        width = clip.width;
        height = clip.height;
        image = alphabet.getSubimage(clip.x, clip.y, clip.width, clip.height);
        this.alphabet = alphabet;
        x = clip.x;
        y = clip.y;
    }

    @Override
    public void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g) {
        g.drawImage(alphabet, position.x * width, position.y * height, position.x * width + width, position.y * height + height, x, y, x + width, y + height, null);

    }

    /*
    Got the following results for various rendering approaches
        if(false) {
            Image bf = map.get(colourPair);
            if (bf == null) {
                bf = new BufferedImage(converter.getColorModelFor(colourPair), image.getRaster(), false, null);
                map.put(colourPair, bf);
                // 3.74, 3.79, 3.82
            }
            g.drawImage(bf, position.x * width, position.y * height, null);
        } else if (false) {
            Image image = map.get(colourPair);
            if (image == null) {
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                image = gc.createCompatibleImage(8, height);
                // 8 bits wide -> 3.93
                // 5 bits wide -> 3.79,3.86,3.91
                Image temp = new BufferedImage(converter.getColorModelFor(colourPair), this.image.getRaster(), false, null);
                image.getGraphics().drawImage(temp, 0, 0, null);
                map.put(colourPair, image);
                System.out.println(".... " + ((Graphics2D)image.getGraphics()).getDeviceConfiguration().getColorModel().getPixelSize());
                System.out.println(".... " + g.getDeviceConfiguration().getColorModel().getPixelSize());
            }
            g.drawImage(image, position.x * width, position.y * height, null);
        } else if (false) {
            Image image = map.get(colourPair);
            if (image == null) {
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                image = gc.createCompatibleVolatileImage(8, height, Transparency.OPAQUE);
                // 8 bits wide -> 3.82,3.88,3.89
                // 5 bits wide -> 3.68,3.85,3.86
                image.setAccelerationPriority(0.0f);
                Image temp = new BufferedImage(converter.getColorModelFor(colourPair), this.image.getRaster(), false, null);
                image.getGraphics().drawImage(temp, 0, 0, null);
                map.put(colourPair, image);
                System.out.println(".... " + ((Graphics2D)image.getGraphics()).getDeviceConfiguration().getColorModel().getPixelSize());
                System.out.println(".... " + g.getDeviceConfiguration().getColorModel().getPixelSize());
            }
            //            total -= System.nanoTime();
            g.drawImage(image, position.x * width, position.y * height, null);
            //            total += System.nanoTime();
        } else if (false) {
//            g.drawChars(new char[] {'A'}, 0, 1, position.x * width, position.y * height);
            // 5.57, 5.86
            g.drawChars(new char[] {'A', 'B', 'C', 'D'}, 0, 4, position.x * width, position.y * height);
            // 12.2
        } else {
            g.drawImage(alphabet, position.x * width, position.y * height, position.x * width + width, position.y * height + height, x, y, x + width, y + height, null);
            // 3.55,3.55,3.55!!!
        }

        */

    /*
    public AwtGlyph(AwtColorConverter converter, BufferedImage alphabet, Rectangle clip) {
        this.converter = converter;
        this.alphabet = alphabet;
        x = clip.x;
        y = clip.y;
        width = clip.width;
        height = clip.height;
    }

    @Override
    public void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g) {
        //        BufferedImage bf = map.get(colourPair);
        //        if (bf == null) {
        //            bf = new BufferedImage(converter.getColorModelFor(colourPair), image.getRaster(), false, null);
        //            map.put(colourPair, bf);
        //        }
        //        g.drawImage(bf, position.x * width, position.y * height, null);
        g.drawImage(alphabet, position.x * width, position.y * height, width, height, x, y, width, height, null);
        //        g.getDeviceConfiguration().getColorModel().
    }

     */

}
