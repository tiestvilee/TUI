package org.tiestvilee.tui.primitives;


import java.awt.Graphics2D;
import java.io.Serializable;

public class Tixel implements Serializable {

    public final Glyph glyph;
    public final ColourPair colourPair;

    public Tixel(Glyph glyph, ColourPair colourPair) {
		if(glyph == null) throw new IllegalArgumentException("glyph not provided");
        this.glyph = glyph;
        this.colourPair = colourPair;
    }

    public void renderAt$On(Position position, Graphics2D g) {
        glyph.renderAt$WithColours$Onto(position, colourPair, g);
    }

}
