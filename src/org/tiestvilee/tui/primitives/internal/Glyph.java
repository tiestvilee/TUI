package org.tiestvilee.tui.primitives.internal;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Position;

import java.awt.Graphics2D;
import java.io.Serializable;

public interface Glyph {

    void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g);

}
