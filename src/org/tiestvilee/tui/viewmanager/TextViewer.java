package org.tiestvilee.tui.viewmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.tiestvilee.tui.Tui;
import org.tiestvilee.tui.awt.KeyMapping;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.internal.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.view.PlainTextView;
import org.tiestvilee.tui.view.ViewBuffer;

public class TextViewer {

    private PlainTextView textView;

    public TextViewer(ViewBuffer view, Map<Character, Glyph> characterMap) {        
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
        if(keyChar > 31) {
            textView.write$AtCursorAndProceed(keyChar);
        }
    }

    public void release(char keyChar, int keyCode) {
        // TODO Auto-generated method stub
        
    }

    public void press(char keyChar, int keyCode) {
        switch(keyCode) {
            case KeyMapping.LEFT:
                textView.setCursor(textView.getCursor().offsetBy(new Position(-1,0)));
                break;
            case KeyMapping.RIGHT:
                textView.setCursor(textView.getCursor().offsetBy(new Position(1,0)));
                break;
            case KeyMapping.UP:
                textView.setCursor(textView.getCursor().offsetBy(new Position(0,-1)));
                break;
            case KeyMapping.DOWN:
                textView.setCursor(textView.getCursor().offsetBy(new Position(0,1)));
                break;
            case KeyMapping.BACKSPACE:
                Position newPosition = textView.getCursor().offsetBy(new Position(-1,0));
                textView.setPosition$To(newPosition, ' ');
                textView.setCursor(newPosition);
                break;
        }
    }
}
