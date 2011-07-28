package org.tiestvilee.tui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.tiestvilee.tui.awt.AwtCanvas;
import org.tiestvilee.tui.awt.AwtEmptyGlyph;
import org.tiestvilee.tui.awt.GlyphToAlphabetMapper;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.primitives.internal.CharacterMap;
import org.tiestvilee.tui.primitives.internal.Glyph;
import org.tiestvilee.tui.view.ViewBuffer;
import org.tiestvilee.tui.view.View.ElementAction;

public class SpeedTest {

    public static final boolean SET_DISPLAY_MODE = false;

    public static void main(String[] args) throws Exception {

        // Determine if full-screen mode is supported directly
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        final DisplayMode old = gs.getDisplayMode();

        System.out.println(Toolkit.getDefaultToolkit().getDesktopProperty("sun.awt.enableExtraMouseButtons"));
        System.out.println(MouseInfo.getNumberOfButtons());

        CharacterMap characterMap = loadCharacterMap();
        final ViewBuffer view = new ViewBuffer(new Rectangle(640 / GlyphToAlphabetMapper.WIDTH, 480 / GlyphToAlphabetMapper.HEIGHT),
                getEmptyTixel(characterMap));

        final Random rand = new Random();
        final ColourPair colourPair = new ColourPair(new Colour(1.0f, Hue.BLACK), new Colour(1.0f, Hue.WHITE));
        fillWithRandomShit(view, rand, colourPair);

        final AwtCanvas canvas = new AwtCanvas(characterMap, view);

        try {
            JFrame win;
            if (SET_DISPLAY_MODE) {
                win = enterFullScreenMode(gs, old, canvas);
            } else {
                win = createEnclosingFrame(canvas);
            }

            win.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });
            canvas.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        fillWithRandomShit(view, rand, colourPair);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            canvas.showYourself();

        } finally {
            // Exit full-screen mode
            if (gs.isDisplayChangeSupported()) {
                gs.setDisplayMode(old);
                gs.setFullScreenWindow(null);
            }
        }
    }

    private static void fillWithRandomShit(final ViewBuffer view,
            final Random rand, final ColourPair colourPair) {
        view.forEachElementDo(new ElementAction() {
            @Override
            public void action(Position position, Tixel tixel) {
                view.setPosition$To(position, new Tixel((char) ('a' + rand.nextInt(26)), colourPair));
            }
        });
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

    private static Tixel getEmptyTixel(CharacterMap characterMap) {
        return new Tixel(' ', new ColourPair(new Colour(1.0f, Hue.RED), new Colour(0.0f, Hue.RED)));
    }

    private static CharacterMap loadCharacterMap() {
        Glyph emptyGlyph = new AwtEmptyGlyph(GlyphToAlphabetMapper.WIDTH, GlyphToAlphabetMapper.HEIGHT);
        CharacterMap characterMap = (new GlyphToAlphabetMapper()).loadMap(emptyGlyph);
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
