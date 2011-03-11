package org.tiestvilee.tui.viewmanager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.view.ViewBuffer;

public class ViewManager implements Runnable {

    private final ViewBuffer view;
    private final Map<Character, Glyph> characterMap;

    public ViewManager(ViewBuffer view, Map<Character, Glyph> characterMap) {
        this.view = view;
        this.characterMap = characterMap;
    }

    public KeyListener getKeyListener() {
        final CommandWidget commandWidget = new CommandWidget();
        final TextViewer focusedView = new TextViewer(view, characterMap);
        
        return new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                commandWidget.press(e.getKeyChar());
                if( ! commandWidget.consume()) {
                    focusedView.press(e.getKeyChar(), e.getKeyCode());
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(commandWidget.consume()) {
                    String command = commandWidget.release(e.getKeyChar());
                    if(command != null) {
                        //exampleFile.txt
                        focusedView.writeFile$To$Using(command);
                    } else {
                        focusedView.type(' ');
                    }
                } else {
                    focusedView.release(e.getKeyChar(), e.getKeyCode());
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                if(commandWidget.consume()) {
                    commandWidget.type(e.getKeyChar());
                } else if(e.getKeyChar() != KeyEvent.VK_UNDEFINED) {
                    focusedView.type(e.getKeyChar());
                }
            }
        };
    }

    @Override
    public void run() {
    }

}
