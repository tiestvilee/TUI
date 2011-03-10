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
		if(colorModel == null) {
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
}
