import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.tiestvilee.tui.awt.AwtCanvas;
import org.tiestvilee.tui.awt.AwtGlyph;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.ViewBuffer;

public class TakeControlOfScreen {
	public static void main(String[] args) throws Exception {

		try {
			Thread.sleep(2000);
		} catch (Exception e) {

		}

		// Determine if full-screen mode is supported directly
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		if (gs.isFullScreenSupported()) {
			// Full-screen mode is supported
		} else {
			// Full-screen mode will be simulated
		}

		final DisplayMode old = gs.getDisplayMode();

		Glyph emptyGlyph = new AwtGlyph(new int[5][8]);
		Tixel emptyTixel = new Tixel(emptyGlyph, new Colour(1.0f, Hue.RED), new Colour(0.0f, Hue.RED));

		Glyph xGlyph = new AwtGlyph(new int[5][8]);
		Tixel xTixel = new Tixel(xGlyph, new Colour(1.0f, Hue.BLUE), new Colour(0.0f, Hue.BLUE));

		ViewBuffer view = new ViewBuffer(new Rectangle(128, 60), emptyTixel);
		// ViewBuffer view = new ViewBuffer(new Rectangle(61,51), emptyTixel);
		view.setPosition$To(new Position(5, 5), xTixel);
		view.setPosition$To(new Position(50, 50), xTixel);
		view.setPosition$To(new Position(150, 150), xTixel);

		final boolean[] byebye = new boolean[1];

		// Create a button that leaves full-screen mode
		Canvas canvas = new AwtCanvas(view);
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				// Return to normal windowed mode
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice gs = ge.getDefaultScreenDevice();
				gs.setDisplayMode(old);
				gs.setFullScreenWindow(null);
				byebye[0] = true;
			}
		});

		// Create a window for full-screen mode; add a button to leave
		// full-screen mode
		Frame frame = new Frame(gs.getDefaultConfiguration());
		frame.setSize(640, 480);
		Window win = new Window(frame);
		win.add(canvas, BorderLayout.CENTER);

		try {
			// Enter full-screen mode
			gs.setFullScreenWindow(win);
			DisplayMode dm = new DisplayMode(640, 480, old.getBitDepth(), old.getRefreshRate());
			gs.setDisplayMode(dm);
			win.validate();

			Thread.sleep(1000);
			view.setPosition$To(new Position(6, 6), xTixel);
			view.setPosition$To(new Position(0, 50), xTixel);
			canvas.repaint();

			Thread.sleep(1000);
			view.setPosition$To(new Position(16, 6), xTixel);
			view.setPosition$To(new Position(40, 0), xTixel);
			canvas.repaint();

			while (byebye[0] == false)
				Thread.sleep(100);

			// ...
		} finally {
			// Exit full-screen mode
			if (gs.isDisplayChangeSupported()) {
				gs.setDisplayMode(old);
				gs.setFullScreenWindow(null);
			}
		}
	}
}
