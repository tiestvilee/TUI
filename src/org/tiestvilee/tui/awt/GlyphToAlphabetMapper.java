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
    public final static int WIDTH = 6;
    public final static int HEIGHT = 10;

    public Map<Character, Glyph> loadMap(Glyph emptyGlyph) {
        Map<Character, Glyph> result = new HashMap<Character, Glyph>();
        BufferedImage alphabet = getAlphabet();

        for (int i = '!'; i <= '~'; i++) {
            int xOffset = 10 + ((i - '!') * WIDTH);
            result.put(new Character((char) i), new AwtGlyph(alphabet, new Rectangle(xOffset, 7, WIDTH, HEIGHT)));
        }
        result.put(' ', emptyGlyph);

        return result;
    }

    private BufferedImage getAlphabet() {
        try {
            URL url = getClass().getClassLoader().getResource("org/tiestvilee/tui/awt/letters6x10-10,7.png");

            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("failed to load alphabet");
        }
    }
}
