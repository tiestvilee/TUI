package org.tiestvilee.tui.viewmanager;

public class CommandWidget {

    private boolean spacePressed;
    private StringBuffer command = new StringBuffer();

    public void press(char c) {
        if(c == ' ') {
            spacePressed = true;
        }
    }

    public void type(char c) {
        if(spacePressed && c != ' ') {
            command.append(c);
        }
    }

    public String release(char c) {
        if(spacePressed && c == ' ') {
            String result = command.toString();
            command.setLength(0);
            spacePressed = false;
            return result;
        }
        return null;
    }

    public boolean consume() {
        return spacePressed;
    }

}
