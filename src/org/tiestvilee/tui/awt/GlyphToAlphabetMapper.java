package org.tiestvilee.tui.awt;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Rectangle;

public class GlyphToAlphabetMapper {
	public Map<Character,Glyph> loadMap(Glyph emptyGlyph) {
		Map<Character,Glyph> result = new HashMap<Character, Glyph>();
		BufferedImage alphabet = getAlphabet();
		
		for(int i='!'; i<='~'; i++) {
			int xOffset = 2+((i-'!')*5);
			result.put(new Character((char)i), new AwtGlyph(alphabet, new Rectangle(xOffset, 5, 5, 8)));
		}
		result.put(' ', emptyGlyph);
		
		return result;
	}
	
	private BufferedImage getAlphabet() {
		try {
			URL url = getClass().getClassLoader().getResource("org/tiestvilee/tui/awt/letters5x8-2,5.png");
			
			return ImageIO.read(url);
		} catch(IOException e) {
			throw new RuntimeException("failed to load alphabet");
		}
	}
}
