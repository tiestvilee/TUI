package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;

public class AwtGlyph implements Glyph {

    private final int width;
    private final int height;
    private transient final BufferedImage image;
    private transient final Map<ColourPair, BufferedImage> map = new HashMap<ColourPair, BufferedImage>();

    public AwtGlyph(BufferedImage image) {
        this.image = image;
        width = image.getHeight(null);
        height = image.getWidth(null);
    }

    public AwtGlyph(BufferedImage alphabet, Rectangle clip) {
        width = clip.width;
        height = clip.height;
        image = alphabet.getSubimage(clip.x, clip.y, clip.width, clip.height);
    }

    @Override
    public void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g) {
        BufferedImage bf = map.get(colourPair);
        if (bf == null) {
            bf = new BufferedImage(colourPair.getColorModel(), image.getRaster(), false, null);
            map.put(colourPair, bf);
        }
        g.drawImage(bf, position.x * width, position.y * height, null);
    }

}
