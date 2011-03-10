package org.tiestvilee.tui.awt;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View;
import org.tiestvilee.tui.view.View.ElementAction;

public class AwtCanvas extends Canvas {

	private static final long serialVersionUID = -6713151784799490817L;

	public final View view;
	public boolean running = true;
	
	private BufferStrategy strategy;

	public AwtCanvas(View view) {
		this.view = view;
		setBounds(0,0,640,480);
		setIgnoreRepaint(true);
	}

	public void showYourself() {
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		long currentTime = System.currentTimeMillis();
		while(running) {
			paintIt();
			try{
				// 30 frames per second
				Thread.sleep((1000/30) - (System.currentTimeMillis() - currentTime)); 
			}catch(Exception e){
				// do nothing
			}
		}
	}

	public void paintIt() {
		final Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		
		long start = System.currentTimeMillis();
		
		view.forEachElementDo(new ElementAction() {
			@Override
			public void action(Position position, Tixel tixel) {
				tixel.renderAt$On(position, g);
			}
		});
		
		long end = System.currentTimeMillis();
		System.out.println("one frame took " + (end - start) );

		g.dispose();
		strategy.show();
	}
}
