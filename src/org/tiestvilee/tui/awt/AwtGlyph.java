package org.tiestvilee.tui.awt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.Map;

import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.internal.Glyph;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;

public class AwtGlyph implements Glyph {

    private final int width;
    private final int height;
    private AwtColorConverter converter;
    private final BufferedImage image;
    private final Map<ColourPair, BufferedImage> map = new HashMap<ColourPair, BufferedImage>();

    public AwtGlyph(AwtColorConverter converter, BufferedImage image) {
        this.converter = converter;
        this.image = image;
        width = image.getHeight(null);
        height = image.getWidth(null);
    }

    public AwtGlyph(AwtColorConverter converter, BufferedImage alphabet, Rectangle clip) {
        this.converter = converter;
        width = clip.width;
        height = clip.height;
        image = alphabet.getSubimage(clip.x, clip.y, clip.width, clip.height);
    }

    @Override
    public void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g) {
        BufferedImage bf = map.get(colourPair);
        if (bf == null) {
            bf = new BufferedImage(converter.getColorModelFor(colourPair), image.getRaster(), false, null);
            map.put(colourPair, bf);
        }
        g.drawImage(bf, position.x * width, position.y * height, null);
    }

}
