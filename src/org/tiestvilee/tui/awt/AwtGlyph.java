package org.tiestvilee.tui.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;

public class AwtGlyph implements Glyph {

	private final int width;
	private final int height;
	private final BufferedImage image;

	public AwtGlyph(BufferedImage image) {
		this.image = image;
		width = image.getHeight(null);
		height = image.getWidth(null);
	}

	public AwtGlyph(BufferedImage alphabet, Rectangle clip) {
		width = clip.width;
		height = clip.height;
		image = alphabet.getSubimage(clip.x, clip.y, clip.width, clip.height);
	}

	@Override
	public void renderAt$On(Position position, Colour fore, Colour back, Graphics2D g) {
//		g.setColor(back.getColor());
//		g.fillRect(position.x * width, position.y * height, width, height);
//		g.setColor(fore.getColor());
		g.drawImage(image, position.x * width, position.y * height, null);
	}

}
