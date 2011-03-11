package org.tiestvilee.tui.view;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.StubGlyph;
import org.tiestvilee.tui.primitives.Tixel;

public class PlainTextViewTest {

	static final Glyph glyphA = new StubGlyph();
	static final Glyph glyphB = new StubGlyph();
    static final Glyph glyphC = new StubGlyph();
    static final Tixel emptyTixel = new Tixel(null, null);
    static final Map<Character, Glyph> map = new HashMap<Character, Glyph>();
    
    @Before
    public void setUp() {
        map.put('a', glyphA);
        map.put('b', glyphB);
        map.put('c', glyphC);
    }

	@Test
	public void shouldWriteCharactersAsTixels() {
		// Given
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		PlainTextView textView = new PlainTextView(view, map, new ColourPair(new Colour(0.0f, Hue.BLUE), new Colour(1.0f, Hue.BLUE)));
		
		// When
		textView.setPosition$To(new Position(1,1), 'a');
		textView.setPosition$To(new Position(2,2), 'b');
		textView.setPosition$To(new Position(3,3), 'c');
		
		// Then
		assertEquals(view.getTixelAt(new Position(1,1)).glyph, glyphA);
        assertEquals(view.getTixelAt(new Position(2,2)).glyph, glyphB);
        assertEquals(view.getTixelAt(new Position(3,3)).glyph, glyphC);
	}
	
}
