package org.tiestvilee.tui.awt;

import java.awt.*;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.internal.Glyph;
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
        Color color = Color.getHSBColor(colourPair.back.hue.getAwtHue(), colourPair.back.hue.getAwtSaturation(), colourPair.back.intensity);

        g.setColor(color);
        g.fillRect(position.x * width, position.y * height, width, height);
    }

}
