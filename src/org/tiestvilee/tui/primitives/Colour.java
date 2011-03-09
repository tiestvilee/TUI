package org.tiestvilee.tui.primitives;

import java.awt.Color;

public class Colour {

	public final float intensity;
	public final Hue hue;
	private final Color color;

	public Colour(float intensity, Hue hue) {
		this.intensity = intensity;
		this.hue = hue;
		color = Color.getHSBColor(hue.getAwtHue(), 1.0f, intensity);
	}

	public Color getColor() {
		return color;
	}
}
