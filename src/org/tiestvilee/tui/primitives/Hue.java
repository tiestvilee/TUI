package org.tiestvilee.tui.primitives;

import java.awt.Color;

public class Hue {

	public static final Hue RED = new Hue();
	public static final Hue BLUE = new Hue();

	public float getAwtHue() {
		if (this == RED) {
			return Color.RGBtoHSB(256, 0, 0, null)[0];
		}
		if (this == BLUE) {
			return Color.RGBtoHSB(0, 256, 0, null)[0];
		}
		return 0;
	}

}
