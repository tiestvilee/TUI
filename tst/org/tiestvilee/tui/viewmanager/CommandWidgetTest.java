package org.tiestvilee.tui.viewmanager;

import static org.junit.Assert.*;

import org.junit.Test;


public class CommandWidgetTest {

    @Test
    public void shouldReturnStringEnteredWhileSpaceHeldDown() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type('a');
        commandWidget.type('b');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, "ab");
    }
    
    @Test
    public void shouldIgnoreStringsEnteredWhileSpaceNotHeldDown() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.type('a');
        commandWidget.type('b');
        String command = commandWidget.release('c');
        
        // then
        assertEquals(command, null);
    }

    @Test
    public void shouldReturnStringWhenSpaceReleased() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type('a');
        String command = commandWidget.release('a');
        
        // then
        assertEquals(command, null);
    }

    @Test
    public void shouldCopeWithTwoCommandsInARow() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type('a');
        commandWidget.release(' ');
        
        commandWidget.press(' ');
        commandWidget.type('b');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, "b");
    }

    @Test
    public void shouldWantToConsumeAllCharsWhileSpaceHeldDown() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        assertFalse(commandWidget.consume());
        commandWidget.press(' ');
        assertTrue(commandWidget.consume());
        commandWidget.type('a');
        assertTrue(commandWidget.consume());
        commandWidget.type('b');
        assertTrue(commandWidget.consume());
        commandWidget.release(' ');
        assertFalse(commandWidget.consume());
    }

    @Test
    public void shouldIgnoreSpaces() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type('a');
        commandWidget.type(' ');
        commandWidget.type('b');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, "ab");
    }
}
