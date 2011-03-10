package org.tiestvilee.tui.primitives;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;

public class TixelTest {

	@Test
	public void shouldContainAGlyphForegroundAndBackgroundColours() {
		Glyph glyph = new StubGlyph();
		Colour red = new Colour(96, Hue.RED);
		Colour blue = new Colour(96, Hue.BLUE);
		
		Tixel tixel = new Tixel(glyph, new ColourPair(red, blue));
		
		assertEquals(tixel.glyph, glyph);
		assertEquals(tixel.colourPair.fore, red);
		assertEquals(tixel.colourPair.back, blue);
	}
}
