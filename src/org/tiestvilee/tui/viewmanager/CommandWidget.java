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
    }

    public String release(char c) {
        if(spacePressed && c == ' ') {
            String result = command.toString();
            command.setLength(0);
            spacePressed = false;
            if(result.length() > 0) {
                return result;
            }
        } else {
            if(spacePressed && c != ' ') {
                command.append(c);
            }
        }
        return null;
    }

    public boolean consume() {
        return spacePressed;
    }

}
