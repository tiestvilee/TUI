package org.tiestvilee.tui.primitives.internal;

import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.internal.Glyph;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;

/**
* Created by IntelliJ IDEA.
* User: tiestvilee
* Date: 21/03/2011
* Time: 14:42
* To change this template use File | Settings | File Templates.
*/
public class CharacterMap implements Serializable {
    Map<Character, Glyph> characterMap;

    public CharacterMap(Map<Character, Glyph> characterMap) {
        this.characterMap = characterMap;
    }

    public Glyph get(char c) {
        if(characterMap.containsKey(c)) {
          return characterMap.get(c);
        }
        return characterMap.get(' ');
    }

    public Glyph get(String s) {
        return get(s.charAt(0));
    }

    public void render$At$WithColours$Onto(char glyph, Position position, ColourPair colourPair, Graphics2D g) {
        characterMap.get(glyph).renderAt$WithColours$Onto(position, colourPair, g);
    }
}
