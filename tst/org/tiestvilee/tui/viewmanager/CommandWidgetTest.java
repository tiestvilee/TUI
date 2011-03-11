package org.tiestvilee.tui.viewmanager;

import static org.junit.Assert.*;

import org.junit.Test;


public class CommandWidgetTest {

    @Test
    public void shouldReturnStringEnteredWhileSpaceHeldDown() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type(' ');
        pressTypeRelease(commandWidget, 'a');
        pressTypeRelease(commandWidget, 'b');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, "ab");
    }

    @Test
    public void shouldReturnNullWhenEmptyCommand() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type(' ');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, null);
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
    public void shouldReturnStringOnlyWhenSpaceReleased() {
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
        commandWidget.type(' ');
        pressTypeRelease(commandWidget, 'a');
        commandWidget.release(' ');
        
        commandWidget.press(' ');
        pressTypeRelease(commandWidget, 'b');
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
        commandWidget.type(' ');
        commandWidget.press(' ');
        pressTypeRelease(commandWidget, 'a');
        commandWidget.type(' ');
        pressTypeRelease(commandWidget, 'b');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, "ab");
    }

    @Test
    public void shouldIgnoreInterleavedChars() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type(' ');
        commandWidget.press('a');
        commandWidget.type('a');
        String command = commandWidget.release(' ');
        commandWidget.release('a');
        
        // then
        assertEquals(command, null);
    }

    @Test
    public void stupidTestCase() {
        CommandWidget commandWidget = new CommandWidget();
        
        // when
        commandWidget.press(' ');
        commandWidget.type(' ');
        commandWidget.release(' ');
        pressTypeRelease(commandWidget, 'a');
        commandWidget.press(' ');
        commandWidget.type(' ');
        String command = commandWidget.release(' ');
        
        // then
        assertEquals(command, null);
    }
    
    private void pressTypeRelease(CommandWidget commandWidget, char c) {
        commandWidget.press(c);
        commandWidget.type(c);
        commandWidget.release(c);
    }

}
