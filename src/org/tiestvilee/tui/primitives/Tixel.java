package org.tiestvilee.tui.primitives;


import org.tiestvilee.tui.primitives.internal.CharacterMap;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Tixel implements Serializable {

    public final char glyph;
    public final ColourPair colourPair;

    public Tixel(char glyph, ColourPair colourPair) {
        this.glyph = glyph;
        this.colourPair = colourPair;
    }

    public Tixel(String glyph, ColourPair colourPair) {
        this.glyph = glyph.charAt(0);
        this.colourPair = colourPair;
    }

    public void renderAt$On(Position position, CharacterMap map, Graphics2D g) {
        map.render$At$WithColours$Onto(glyph, position, colourPair, g);
    }

}
