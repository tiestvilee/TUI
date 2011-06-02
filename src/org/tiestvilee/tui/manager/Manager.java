package org.tiestvilee.tui.manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.tiestvilee.tui.primitives.internal.Glyph;
import org.tiestvilee.tui.view.ViewBuffer;

public class Manager implements Runnable {
    private final JavascriptRunner runner;
    private final ExecutorService executor;

    public Manager(JavascriptRunner imageRunner, ExecutorService executor) {
        runner = imageRunner;
        this.executor = executor;
    }

    private void loadObjectsIntoRunner(ViewBuffer view,
            Map<Character, Glyph> characterMap) {
        runner.addObject$As$(view, "view");
        runner.addObject$As$(characterMap, "characterMap");
    }

    public KeyListener getKeyListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent keyEvent) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        runner.addObject$As$(keyEvent, "latestKeyEvent");
                        runner.evaluate("image.eventHandler.keyTyped(latestKeyEvent)");
                    }
                });
            }

            @Override
            public void keyPressed(final KeyEvent keyEvent) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("java : got keyEvent "
                                + keyEvent.getKeyCode());
                        runner.addObject$As$(keyEvent, "latestKeyEvent");
                        runner.evaluate("image.eventHandler.keyPressed(latestKeyEvent)");
                    }
                });
            }

            @Override
            public void keyReleased(final KeyEvent keyEvent) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        runner.addObject$As$(keyEvent, "latestKeyEvent");
                        runner.evaluate("image.eventHandler.keyReleased(latestKeyEvent)");
                    }
                });
            }
        };
    }

    public MouseListener getMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MouseEvent ha = mouseEvent;
                System.out.println(ha);
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                MouseEvent ha = mouseEvent;
                System.out.println(ha);
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }
        };
    }

    @Override
    public void run() {

    }
}
