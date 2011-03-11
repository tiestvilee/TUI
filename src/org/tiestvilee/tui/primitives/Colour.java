package org.tiestvilee.tui.primitives;

import java.awt.Color;

public class Colour {

    public final float intensity;
    public final Hue hue;
    private final Color color;

    public Colour(float intensity, Hue hue) {
        this.intensity = intensity;
        this.hue = hue;
        color = Color.getHSBColor(hue.getAwtHue(), hue.getAwtSaturation(), intensity);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Colour other = (Colour) obj;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        return true;
    }
}
