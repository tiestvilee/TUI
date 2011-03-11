package org.tiestvilee.tui.viewmanager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.tiestvilee.tui.Tui;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.ViewBuffer;

public class ViewManager implements Runnable {

    private final ViewBuffer view;
    private final Map<Character, Glyph> characterMap;

    public ViewManager(ViewBuffer view, Map<Character, Glyph> characterMap) {
        this.view = view;
        this.characterMap = characterMap;
    }

    public KeyListener getKeyListener() {
        return new KeyAdapter() {
        };
    }

    @Override
    public void run() {
        writeFile$To$Using("exampleFile.txt", view, characterMap);

    }

    private static void writeFile$To$Using(String fileName, ViewBuffer view, Map<Character, Glyph> characterMap) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((new Tui()).getClass().getClassLoader()
                .getResourceAsStream(fileName)));

            Colour fore = new Colour(1.0f, Hue.RED);
            Colour back = new Colour(0.0f, Hue.RED);
            ColourPair colourPair = new ColourPair(fore, back);

            String line;
            int y = 0;
            while ((line = reader.readLine()) != null && y < 60) {
                for (int x = 0; x < line.length(); x++) {
                    Glyph glyph = characterMap.get(new Character(line.charAt(x)));
                    if (glyph != null) {
                        view.setPosition$To(new Position(x, y), new Tixel(glyph, colourPair));
                    }
                }
                y++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
