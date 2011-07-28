package org.tiestvilee.tui.awt;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.primitives.internal.CharacterMap;
import org.tiestvilee.tui.view.ViewBuffer;
import org.tiestvilee.tui.view.View.ElementAction;

public class AwtCanvas extends Canvas {

    private static final long serialVersionUID = -6713151784799490817L;
    private static final boolean VERBOSE = false;

    public final ViewBuffer view;
    public boolean running = true;

    private BufferStrategy strategy;
    private CharacterMap characterMap;


    public AwtCanvas(CharacterMap characterMap, ViewBuffer view) {
        this.characterMap = characterMap;
        this.view = view;
        setBounds(0, 0, 640, 480);
        setIgnoreRepaint(true);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 8);
        setFont(font);
    }

    public void showYourself() {
        setUpBufferStrategy();
        paintWholeScreen();
        paintWholeScreen();

        while (running) {
            long currentTime = System.currentTimeMillis();
            paintWholeScreen();
            ensure30FramesASecond(currentTime);
        }
    }

    private void setUpBufferStrategy() {
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    private void ensure30FramesASecond(long currentTime) {
        try {
            long timeToWait = (1000 / 30) - (System.currentTimeMillis() - currentTime);
            if(timeToWait < 0) {
                timeToWait = 100;
                System.out.println("timed out, waiting a bit to see if we can catch up " + (System.currentTimeMillis() - currentTime));
            }
            Thread.sleep(timeToWait);
        } catch (Exception e) {
            // do nothing
        }
    }

    private void paintWholeScreen() {
        final Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        view.forEachElementDo(new ElementAction() {
            @Override
            public void action(Position position, Tixel tixel) {
                tixel.renderAt$UsingCharacters$Onto(position, characterMap, g);
            }
        });

        g.dispose();
        strategy.show();
    }

}
