package org.tiestvilee.tui.awt;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View.ElementAction;
import org.tiestvilee.tui.view.ViewBuffer;

public class AwtCanvas extends Canvas {

	private static final long serialVersionUID = -6713151784799490817L;
	private static final boolean VERBOSE = false;

	public final ViewBuffer view;
	public boolean running = true;
	
	private BufferStrategy strategy;
	private Set<Position> oldDirtyElements = new HashSet<Position>();

	public AwtCanvas(ViewBuffer view) {
		this.view = view;
		setBounds(0,0,640,480);
		setIgnoreRepaint(true);
	}

	public void showYourself() {
		setUpBufferStrategy();
		paintWholeScreen();
		paintWholeScreen();
		
		long currentTime = System.currentTimeMillis();
		while(running) {
			Set<Position> dirtyElements = view.getDirtyElementsAndClearThem();
			oldDirtyElements.addAll(dirtyElements);
			paintChangesSinceLastFrame(oldDirtyElements);
			oldDirtyElements  = dirtyElements;
			ensure30FramesASecond(currentTime);
		}
	}

	private void setUpBufferStrategy() {
		createBufferStrategy(2); 
		strategy = getBufferStrategy();
	}

	private void ensure30FramesASecond(long currentTime) {
		try{
			Thread.sleep((1000/30) - (System.currentTimeMillis() - currentTime)); 
		}catch(Exception e){
			// do nothing
		}
	}

	private void paintWholeScreen() {
		final Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		
		view.forEachElementDo(new ElementAction() {
			@Override
			public void action(Position position, Tixel tixel) {
				tixel.renderAt$On(position, g);
			}
		});

		g.dispose();
		strategy.show();
	}

	public void paintChangesSinceLastFrame(Collection<Position> dirtyElements) {
		drawScreenToBuffer(dirtyElements, strategy);
		strategy.show();
	}

	private void drawScreenToBuffer(Collection<Position> dirtyElements, BufferStrategy strategy2) {
		final Graphics2D g = (Graphics2D) strategy2.getDrawGraphics();
		
		long start;
		if(VERBOSE) {
			start = System.currentTimeMillis();
		}
		
		for(Position position : dirtyElements) {
			view.getTixelAt(position).renderAt$On(position, g);
		}
		
		if(VERBOSE) {
			long end = System.currentTimeMillis();
			if((end-start) == 0) {
				System.out.print('.');
			} else {
				System.out.println("one frame took " + (end - start) );
			}
		}

		g.dispose();
	}
}
