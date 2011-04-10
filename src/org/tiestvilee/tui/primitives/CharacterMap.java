package org.tiestvilee.tui.primitives;

import java.util.Map;

/**
* Created by IntelliJ IDEA.
* User: tiestvilee
* Date: 21/03/2011
* Time: 14:42
* To change this template use File | Settings | File Templates.
*/
public class CharacterMap {
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
}
