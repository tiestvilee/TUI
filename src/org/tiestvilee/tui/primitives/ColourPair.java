package org.tiestvilee.tui.primitives;

import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;

public class ColourPair {
    public final Colour fore;
    public final Colour back;
    private ColorModel colorModel;

    public ColourPair(Colour fore, Colour back) {
        this.fore = fore;
        this.back = back;
    }

    public ColorModel getColorModel() {
        if (colorModel == null) {
            byte[] r = new byte[16];
            byte[] g = new byte[16];
            byte[] b = new byte[16];

            Color backColor = back.getColor();
            r[0] = (byte) backColor.getRed();
            g[0] = (byte) backColor.getGreen();
            b[0] = (byte) backColor.getBlue();

            Color foreColor = fore.getColor();
            r[1] = (byte) foreColor.getRed();
            g[1] = (byte) foreColor.getGreen();
            b[1] = (byte) foreColor.getBlue();

            colorModel = new IndexColorModel(1, 2, r, g, b);
        }
        return colorModel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((back == null) ? 0 : back.hashCode());
        result = prime * result + ((fore == null) ? 0 : fore.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColourPair other = (ColourPair) obj;
        if (back == null) {
            if (other.back != null)
                return false;
        } else if (!back.equals(other.back))
            return false;
        if (fore == null) {
            if (other.fore != null)
                return false;
        } else if (!fore.equals(other.fore))
            return false;
        return true;
    }

}
