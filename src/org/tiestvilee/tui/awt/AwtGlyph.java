package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;

import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Position;

public class AwtGlyph implements Glyph {

	private final int[][] pixels;
	private final int width;
	private final int height;

	public AwtGlyph(int[][] pixels) {
		this.pixels = pixels;
		width = pixels.length;
		height = pixels[0].length;
	}

	@Override
	public void renderAt$On(Position position, Colour fore, Colour back, Graphics2D g) {
		g.setColor(back.getColor());
		g.fillRect(position.x * width, position.y * height, width, height);
		g.setColor(fore.getColor());
		g.fillRect(position.x * width, position.y * height, width, height);
		// g.drawImage(buffer, new AffineTransform(1f,0f,0f,1f,position.x*width,
		// position.y*height), null);
	}

}
