package org.tiestvilee.tui.awt;

import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.Map;

public class AwtColorConverter {
    Map<ColourPair, ColorModel> colorModels = new HashMap<ColourPair, ColorModel>();

    public ColorModel getColorModelFor(ColourPair colourPair) {
        ColorModel colorModel = colorModels.get(colourPair);

        if (colorModel == null) {
            byte[] r = new byte[16];
            byte[] g = new byte[16];
            byte[] b = new byte[16];

            Color backColor = getAwtColorFor(colourPair.back);
            r[0] = (byte) backColor.getRed();
            g[0] = (byte) backColor.getGreen();
            b[0] = (byte) backColor.getBlue();

            Color foreColor = getAwtColorFor(colourPair.fore);
            r[1] = (byte) foreColor.getRed();
            g[1] = (byte) foreColor.getGreen();
            b[1] = (byte) foreColor.getBlue();

            colorModel = new IndexColorModel(1, 2, r, g, b);

            colorModels.put(colourPair, colorModel);
        }
        return colorModel;
    }

    public Color getAwtColorFor(Colour colour) {
        return Color.getHSBColor(colour.hue.getAwtHue(), colour.hue.getAwtSaturation(), colour.intensity);
    }
}
