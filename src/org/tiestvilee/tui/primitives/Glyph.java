package org.tiestvilee.tui.primitives;

import java.awt.Graphics2D;

public interface Glyph {

	void renderAt$WithColours$Onto(Position position, ColourPair colourPair, Graphics2D g);

}
