package org.tiestvilee.tui.viewmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.tiestvilee.tui.Tui;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.view.PlainTextView;
import org.tiestvilee.tui.view.View;

public class FileViewer {

    public FileViewer() {
        
    }
    
    public void writeFile$To$Using(String fileName, View view, Map<Character, Glyph> characterMap) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((new Tui()).getClass().getClassLoader()
                .getResourceAsStream(fileName)));

            Colour fore = new Colour(1.0f, Hue.BLUE);
            Colour back = new Colour(1.0f, Hue.RED);
            ColourPair colourPair = new ColourPair(fore, back);
            
            PlainTextView textView = new PlainTextView(view, characterMap, colourPair);

            String line;
            int y = 0;
            while ((line = reader.readLine()) != null && y < 60) {
                for (int x = 0; x < line.length(); x++) {
                    textView.setPosition$To(new Position(x, y), line.charAt(x));
                }
                y++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
