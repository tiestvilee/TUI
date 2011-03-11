package org.tiestvilee.tui.viewmanager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Rectangle;
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
        
        return new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                commandWidget.press(e.getKeyChar());
                if( ! commandWidget.consume()) {
                    System.out.print("pressed " + e.getKeyChar());
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(commandWidget.consume()) {
                    String command = commandWidget.release(e.getKeyChar());
                    if(command != null) {
                        System.out.println("command " + command);
                        //exampleFile.txt
                        new FileViewer().writeFile$To$Using(command, view.clipTo(new Rectangle(5,5,95,38)), characterMap);
                    }
                } else {
                    System.out.print("released " + e.getKeyChar());
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                if(commandWidget.consume()) {
                    commandWidget.type(e.getKeyChar());
                } else {
                    System.out.print("typed " + e.getKeyChar());
                }
            }
        };
    }

    @Override
    public void run() {
    }

}
