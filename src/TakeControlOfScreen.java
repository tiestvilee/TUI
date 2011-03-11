import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.tiestvilee.tui.awt.AwtCanvas;
import org.tiestvilee.tui.awt.AwtEmptyGlyph;
import org.tiestvilee.tui.awt.GlyphToAlphabetMapper;
import org.tiestvilee.tui.primitives.Colour;
import org.tiestvilee.tui.primitives.ColourPair;
import org.tiestvilee.tui.primitives.Glyph;
import org.tiestvilee.tui.primitives.Hue;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View.ElementAction;
import org.tiestvilee.tui.view.ViewBuffer;

public class TakeControlOfScreen {
	

	public static final boolean SET_DISPLAY_MODE = false;
	
	public static void main(String[] args) throws Exception {

		// Determine if full-screen mode is supported directly
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		if (gs.isFullScreenSupported()) {
			// Full-screen mode is supported
		} else {
			// Full-screen mode will be simulated
		}

		final DisplayMode old = gs.getDisplayMode();

		
		Glyph emptyGlyph = new AwtEmptyGlyph(GlyphToAlphabetMapper.WIDTH, GlyphToAlphabetMapper.HEIGHT); 
		Tixel emptyTixel = new Tixel(emptyGlyph, new ColourPair(new Colour(1.0f, Hue.RED), new Colour(0.0f, Hue.RED)));

		Map<Character, Glyph> characterMap = (new GlyphToAlphabetMapper()).loadMap(emptyGlyph);

		ViewBuffer view = new ViewBuffer(new Rectangle(640/GlyphToAlphabetMapper.WIDTH, 480/GlyphToAlphabetMapper.HEIGHT), emptyTixel);
		
		writeFile$To$Using("exampleFile.txt", view, characterMap);

		// Create a button that leaves full-screen mode
		final AwtCanvas canvas = new AwtCanvas(view);
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				// Return to normal windowed mode
				if(SET_DISPLAY_MODE) {
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					GraphicsDevice gs = ge.getDefaultScreenDevice();
					gs.setDisplayMode(old);
					gs.setFullScreenWindow(null);
				}
				canvas.running = false;
				System.out.println("Done!");
				System.exit(0);
			}
		});

		try {
			if(SET_DISPLAY_MODE) {
				// Enter full-screen mode
				Window win = createEnclosingWindow(canvas, gs);
				gs.setFullScreenWindow(win);
				DisplayMode dm = new DisplayMode(640, 480, old.getBitDepth(), old.getRefreshRate());
				gs.setDisplayMode(dm);
				win.validate();
			} else {
				createEnclosingFrame(canvas);
			}
			
			//(new Thread(new MatrixExample(view, characterMap))).start();
			(new Thread(new ScrollExample(view, emptyTixel))).start();
			
			canvas.showYourself();

		} finally {
			// Exit full-screen mode
			if (gs.isDisplayChangeSupported()) {
				gs.setDisplayMode(old);
				gs.setFullScreenWindow(null);
			}
		}
	}



	private static void writeFile$To$Using(String fileName, ViewBuffer view, Map<Character, Glyph> characterMap) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader((new TakeControlOfScreen()).getClass().getClassLoader().getResourceAsStream(fileName)));
			
			Colour fore = new Colour(1.0f, Hue.RED);
			Colour back = new Colour(0.0f, Hue.RED);
			ColourPair colourPair = new ColourPair(fore, back);
			
			String line;
			int y = 0;
			while( (line = reader.readLine()) != null && y < 60) {
				for(int x=0; x<line.length(); x++) {
					Glyph glyph = characterMap.get(new Character(line.charAt(x)));
					if(glyph != null) {
						view.setPosition$To(new Position(x, y), new Tixel(glyph, colourPair));
					}
				}
				y++;
			}
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	private static Window createEnclosingWindow(AwtCanvas canvas, GraphicsDevice gs) {
		
		Frame frame = new Frame(gs.getDefaultConfiguration());
		frame.setSize(640, 480);
		Window win = new Window(frame);
		win.add(canvas, BorderLayout.CENTER);
		
		return win;
	}

	private static void createEnclosingFrame(AwtCanvas canvas) {
		JFrame container = new JFrame("TUI");
		
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(640,480));
		panel.setLayout(null);

		panel.add(canvas);
		
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
	}
	
	public static class MatrixExample implements Runnable {

		private final ViewBuffer view;
		private final Map<Character, Glyph> characterMap;

		public MatrixExample(ViewBuffer view, Map<Character, Glyph> characterMap) {
			this.view = view;
			this.characterMap = characterMap;
		}

		@Override
		public void run() {
			Tixel t0 = new Tixel(characterMap.get('X'), new ColourPair(new Colour(1.0f, Hue.BLUE), new Colour(0.0f, Hue.BLUE)));
			Tixel t1 = new Tixel(characterMap.get('X'), new ColourPair(new Colour(0.5f, Hue.BLUE), new Colour(0.0f, Hue.BLUE)));
			Random r = new Random();
			
			int[] depth = new int[50];
			Arrays.fill(depth, -1);
			
			for(int i=0; i<1000; i++) {
				
				depth[r.nextInt(50)] = 1;
				
				for(int j=0; j<50; j++) {
					if(depth[j] == -1) {
						continue;
					}
					
					depth[j]++;
					view.setPosition$To(new Position(j, depth[j]), t0);
					view.setPosition$To(new Position(j, depth[j]-1), t1);
				}
				try{
					Thread.sleep(100);
				} catch(Exception e) {
					// ignore
				}
				
			}
		}


	}

	public static class ScrollExample implements Runnable {

		private final ViewBuffer view;
		private final Tixel empty;

		public ScrollExample(ViewBuffer view, Tixel empty) {
			this.view = view;
			this.empty = empty;
		}

		@Override
		public void run() {
			ViewBuffer copy = copyOriginalScreen();
			
			while(true) {
				copy.offsetBy(new Position(0,2)).forEachElementDo(new ElementAction() {
					@Override
					public void action(Position position, Tixel tixel) {
						view.setPosition$To(position, tixel);
					}
				});
				sleep();
				copy.forEachElementDo(new ElementAction() {
					@Override
					public void action(Position position, Tixel tixel) {
						view.setPosition$To(position, tixel);
					}
				});
				sleep();
				copy.offsetBy(new Position(0,-2)).forEachElementDo(new ElementAction() {
					@Override
					public void action(Position position, Tixel tixel) {
						view.setPosition$To(position, tixel);
					}
				});
				sleep();
				copy.forEachElementDo(new ElementAction() {
					@Override
					public void action(Position position, Tixel tixel) {
						view.setPosition$To(position, tixel);
					}
				});
				sleep();
			}
		}

		private void sleep() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private ViewBuffer copyOriginalScreen() {
			final ViewBuffer copy = new ViewBuffer(view.clipRect, empty);
			
			view.forEachElementDo(new ElementAction() {
				@Override
				public void action(Position position, Tixel tixel) {
					copy.setPosition$To(position, tixel);
				}
			});
			
			return copy;
		}
		
	}
}
