package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Position;

public class AwtEmptyGlyph implements Glyph {

    private final int width;
    private final int height;

    public AwtEmptyGlyph(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g) {
        g.setColor(colourPair.back.getColor());
        g.fillRect(position.x * width, position.y * height, width, height);
    }

}
