package org.tiestvilee.tui.awt;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View;
import org.tiestvilee.tui.view.View.ElementAction;

public class AwtCanvas extends Canvas {

	private static final long serialVersionUID = -6713151784799490817L;

	private final View view;

	public AwtCanvas(View view) {
		this.view = view;
	}

	public void paint(final Graphics g) {
		super.paint(g);

		long start = System.currentTimeMillis();
		final long[] total = new long[1];
		view.forEachElementDo(new ElementAction() {
			@Override
			public void action(Position position, Tixel tixel) {
				long start = System.nanoTime();
				tixel.renderAt$On(position, (Graphics2D) g);
				long end = System.nanoTime();
				total[0] += (end - start);
			}
		});
		long end = System.currentTimeMillis();
		System.out.println("total took " + (end - start) + " but stuff on inside took " + total[0] / 1000);
	}
}
