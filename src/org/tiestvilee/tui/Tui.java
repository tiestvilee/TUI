package org.tiestvilee.tui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.tiestvilee.tui.awt.AwtCanvas;
import org.tiestvilee.tui.awt.AwtEmptyGlyph;
import org.tiestvilee.tui.awt.GlyphToAlphabetMapper;
import org.tiestvilee.tui.manager.Manager;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View.ElementAction;
import org.tiestvilee.tui.view.ViewBuffer;
import org.tiestvilee.tui.viewmanager.ViewManager;

public class Tui {

    public static final boolean SET_DISPLAY_MODE = false;

    public static void main(String[] args) throws Exception {

        // Determine if full-screen mode is supported directly
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        final DisplayMode old = gs.getDisplayMode();


        Map<Character, Glyph> characterMap = loadCharacterMap();
        ViewBuffer view = new ViewBuffer(new Rectangle(640 / GlyphToAlphabetMapper.WIDTH, 480 / GlyphToAlphabetMapper.HEIGHT),
            getEmptyTixel(characterMap));
		String image = loadImage();
		Manager manager = new Manager(image, view, characterMap);


        final AwtCanvas canvas = new AwtCanvas(manager.getView());
//        canvas.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent evt) {
//                // Return to normal windowed mode
//                if (SET_DISPLAY_MODE) {
//                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                    GraphicsDevice gs = ge.getDefaultScreenDevice();
//                    gs.setDisplayMode(old);
//                    gs.setFullScreenWindow(null);
//                }
//                canvas.running = false;
//                System.out.println("Done!");
//                System.exit(0);
//            }
//        });
        

        try {
            JFrame win;
            if (SET_DISPLAY_MODE) {
				win = enterFullScreenMode(gs, old, canvas);
            } else {
                win = createEnclosingFrame(canvas);
            }

            win.addKeyListener(manager.getKeyListener());
			win.addMouseListener(manager.getMouseListener());
            (new Thread(manager)).start();

            canvas.showYourself();

        } finally {
            // Exit full-screen mode
            if (gs.isDisplayChangeSupported()) {
                gs.setDisplayMode(old);
                gs.setFullScreenWindow(null);
            }
        }
    }

	private static String loadImage() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("tui.image")));

			String line;
			StringBuffer total = new StringBuffer();
			while((line = reader.readLine()) != null) {
				total.append(line).append("\n");
			}
			return total.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static JFrame enterFullScreenMode(GraphicsDevice gs, DisplayMode old, AwtCanvas canvas) {
		JFrame frame = createEnclosingWindow(canvas, gs);

		gs.setFullScreenWindow(frame);
		DisplayMode dm = new DisplayMode(640, 480, old.getBitDepth(), old.getRefreshRate());
		gs.setDisplayMode(dm);

		frame.validate();
		frame.setFocusable(true);
		return frame;
	}

	private static Tixel getEmptyTixel(Map<Character, Glyph> characterMap) {
        return new Tixel(characterMap.get(' '), new ColourPair(new Colour(1.0f, Hue.RED), new Colour(0.0f, Hue.RED)));
    }

    private static Map<Character, Glyph> loadCharacterMap() {
        Glyph emptyGlyph = new AwtEmptyGlyph(GlyphToAlphabetMapper.WIDTH, GlyphToAlphabetMapper.HEIGHT);
        Map<Character, Glyph> characterMap = (new GlyphToAlphabetMapper()).loadMap(emptyGlyph);
        return characterMap;
    }

    private static JFrame createEnclosingWindow(AwtCanvas canvas, GraphicsDevice gs) {
        JFrame frame = new JFrame(gs.getDefaultConfiguration());

        frame.setSize(640, 480);
		frame.add(canvas, BorderLayout.CENTER);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setFocusableWindowState(true);

        return frame;
    }

	// the following should be the same as the preceeding
    private static JFrame createEnclosingFrame(AwtCanvas canvas) {
        JFrame container = new JFrame("TUI");

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(640, 480));
        panel.setLayout(null);

        panel.add(canvas);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        
        return container;
    }
}
