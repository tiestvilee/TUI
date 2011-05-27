package org.tiestvilee.tui.primitives;

import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.io.Serializable;

public class ColourPair implements Serializable {
    public final Colour fore;
    public final Colour back;

    public ColourPair(Colour fore, Colour back) {
        this.fore = fore;
        this.back = back;
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
