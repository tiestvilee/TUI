package org.tiestvilee.tui.manager;

import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.view.ViewBuffer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class Manager implements Runnable {
	private JavascriptRunner runner;

	public Manager(JavascriptRunner imageRunner) {
		runner = imageRunner;
	}

	private void loadObjectsIntoRunner(ViewBuffer view, Map<Character, Glyph> characterMap) {
		runner.addObject$As$(view, "view");
		runner.addObject$As$(characterMap, "characterMap");
	}

	public KeyListener getKeyListener() {
		return new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
                runner.addObject$As$(keyEvent, "latestKeyEvent");
                runner.evaluate("image.eventHandler.keyTyped(latestKeyEvent)");
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				runner.addObject$As$(keyEvent, "latestKeyEvent");
				runner.evaluate("image.eventHandler.keyPressed(latestKeyEvent)");
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
                runner.addObject$As$(keyEvent, "latestKeyEvent");
                runner.evaluate("image.eventHandler.keyReleased(latestKeyEvent)");
			}
		};
	}

	public MouseListener getMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		};
	}

	@Override
	public void run() {

	}
}
