package org.tiestvilee.tui.primitives;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.internal.Glyph;

public class TixelTest {

	@Test
	public void shouldContainAGlyphForegroundAndBackgroundColours() {
		Colour red = new Colour(96, Hue.RED);
		Colour blue = new Colour(96, Hue.BLUE);
		
		Tixel tixel = new Tixel(' ', new ColourPair(red, blue));
		
		assertEquals(tixel.glyph, ' ');
		assertEquals(tixel.colourPair.fore, red);
		assertEquals(tixel.colourPair.back, blue);
	}
}
