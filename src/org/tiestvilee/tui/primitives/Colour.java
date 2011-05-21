package org.tiestvilee.tui.primitives;

import java.awt.Color;
import java.io.Serializable;

public class Colour implements Serializable {

    public final float intensity;
    public final Hue hue;


    public Colour(float intensity, Hue hue) {
        this.intensity = intensity;
        this.hue = hue;
    }
}
