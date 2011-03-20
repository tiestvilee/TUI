package org.tiestvilee.tui.manager;

import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.view.ViewBuffer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class Manager implements Runnable {
	private ViewBuffer view;
	private JavascriptRunner runner;

	public Manager(String image, ViewBuffer view, Map<Character, Glyph> characterMap) {
		this.view = view;

		runner = new JavascriptRunner();

		runner.init();

		loadObjectsIntoRunner(view, characterMap);
		runner.evaluate("var primitives = org.tiestvilee.tui.primitives;");

		runner.evaluate(image);
	}

	private void loadObjectsIntoRunner(ViewBuffer view, Map<Character, Glyph> characterMap) {
		runner.addObject$As$(view, "view");
		runner.addObject$As$(characterMap, "characterMap");
	}

	public ViewBuffer getView() {
		return view;
	}

	public KeyListener getKeyListener() {
		return new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				runner.addObject$As$(keyEvent, "latestKeyEvent");
				runner.evaluate("manager.keyPressed(latestKeyEvent)");
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
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
