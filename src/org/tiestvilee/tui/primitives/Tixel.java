package org.tiestvilee.tui.primitives;


import java.awt.Graphics2D;
import java.io.Serializable;

import org.tiestvilee.tui.awt.AwtColorConverter;
import org.tiestvilee.tui.primitives.internal.CharacterMap;

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

    public void renderAt$UsingCharacters$Onto(Position position, CharacterMap map, Graphics2D g) {
        if(true) {
            map.render$At$WithColours$Onto(glyph, position, colourPair, g);
        } else {
            g.setBackground(AwtColorConverter.getAwtColorFor(colourPair.back));
            g.setColor(AwtColorConverter.getAwtColorFor(colourPair.fore));
            int x = position.x*6;
            int y = position.y*10;
            g.clearRect(x, y, 6, 10);
            if(glyph > 32) {
                g.drawChars(new char[] {glyph}, 0, 1, x, y + 10 -2);
            }
        }
    }

}
