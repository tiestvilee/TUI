package org.tiestvilee.tui.view;

import java.util.Map;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;

public class PlainTextView {

    private final View view;
    private final Map<Character, Glyph> characterMap;
    private final ColourPair colourPair;
    private Position cursor;

    public PlainTextView(View view, Map<Character, Glyph> characterMap, ColourPair initialColour) {
        this.view = view;
        this.characterMap = characterMap;
        this.colourPair = initialColour;
        cursor = new Position(0,1);
    } 

    public void setPosition$To(Position position, char c) {
        Glyph glyph = characterMap.get(c);
        if(glyph != null) {
            view.setPosition$To(position, new Tixel(glyph, colourPair));
        }
    }

    public void write$AtCursorAndProceed(char keyChar) {
        Glyph glyph = characterMap.get(keyChar);
        if(glyph != null) {
            view.setPosition$To(cursor, new Tixel(glyph, colourPair));
        }
        cursor = cursor.offsetBy(new Position(1,0));
    }
}
