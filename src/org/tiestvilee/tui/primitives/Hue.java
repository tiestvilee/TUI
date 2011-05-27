package org.tiestvilee.tui.primitives;

import java.awt.Color;
import java.io.Serializable;

public enum Hue implements Serializable {

    BLACK, WHITE, RED, GREEN, BLUE;

    public float getAwtHue() {
        if (this == RED) {
            return Color.RGBtoHSB(256, 0, 0, null)[0];
        }
        if (this == GREEN) {
            return Color.RGBtoHSB(0, 256, 0, null)[0];
        }
        if (this == BLUE) {
            return Color.RGBtoHSB(0, 0, 256, null)[0];
        }
        return 0;
    }

    public float getAwtSaturation() {
        if(this == WHITE) {
            return 0.0f;
        }
        return 1.0f;
    }

}
