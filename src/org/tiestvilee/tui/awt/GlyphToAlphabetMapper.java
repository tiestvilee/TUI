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
	public Map<Character,Glyph> loadMap() {
		Map<Character,Glyph> result = new HashMap<Character, Glyph>();
		BufferedImage alphabet = getAlphabet();
		
		for(int i='!'; i<='~'; i++) {
			int xOffset = 3+((i-'!')*5);
			result.put(new Character((char)i), new AwtGlyph(alphabet, new Rectangle(xOffset, 4, 5, 8)));
		}
		
		return result;
	}
	
	private BufferedImage getAlphabet() {
		try {
			URL url = getClass().getClassLoader().getResource("org/tiestvilee/tui/awt/letters5x8-3,4.png");
			
			return ImageIO.read(url);
		} catch(IOException e) {
			throw new RuntimeException("failed to load alphabet");
		}
	}
}
