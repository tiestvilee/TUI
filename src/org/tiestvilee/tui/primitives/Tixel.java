package org.tiestvilee.tui.primitives;

import java.awt.Graphics2D;

public class Tixel {

	public final Glyph glyph;
	public final Colour fore;
	public final Colour back;

	public Tixel(Glyph glyph, Colour fore, Colour back) {
		this.glyph = glyph;
		this.fore = fore;
		this.back = back;
	}

	public void renderAt$On(Position position, Graphics2D g) {
		glyph.renderAt$On(position, fore, back, g);
	}

}
