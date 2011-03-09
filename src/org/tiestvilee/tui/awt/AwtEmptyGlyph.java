package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;

import org.tiestvilee.tui.primitives.Colour;
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
	public void renderAt$On(Position position, Colour fore, Colour back, Graphics2D g) {
		g.setColor(back.getColor());
		g.fillRect(position.x * width, position.y * height, width, height);
	}

}
