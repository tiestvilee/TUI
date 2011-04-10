package org.tiestvilee.tui.primitives;

import java.awt.Graphics2D;
import java.io.Serializable;

public interface Glyph extends Serializable {

    void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g);

}
