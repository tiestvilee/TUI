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
import org.tiestvilee.tui.view.ViewBuffer;

public class TextViewer {

    private final ViewBuffer view;
    private final Map<Character, Glyph> characterMap;
    private PlainTextView textView;

    public TextViewer(ViewBuffer view, Map<Character, Glyph> characterMap) {
        this.view = view;
        this.characterMap = characterMap;
        
        Colour fore = new Colour(1.0f, Hue.WHITE);
        Colour back = new Colour(0.0f, Hue.BLACK);
        ColourPair colourPair = new ColourPair(fore, back);
        textView = new PlainTextView(view, characterMap, colourPair);
    }
    
    public void writeFile$To$Using(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((new Tui()).getClass().getClassLoader()
                .getResourceAsStream(fileName)));

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

    public void type(char keyChar) {
        textView.write$AtCursorAndProceed(keyChar);
    }

    public void release(char keyChar, int keyCode) {
        // TODO Auto-generated method stub
        
    }

    public void press(char keyChar, int keyCode) {
        // TODO Auto-generated method stub
        
    }
}
