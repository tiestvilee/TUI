package org.tiestvilee.tui.primitives;

import java.awt.Graphics2D;

public interface Glyph {

	void renderAt$On(Position position, Colour fore, Colour back, Graphics2D g);

}
