package org.tiestvilee.tui.view;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.tiestvilee.tui.primitives.*;
import org.tiestvilee.tui.primitives.internal.Glyph;
import org.tiestvilee.tui.view.View.ElementAction;

public class ViewBufferTest {
    static final Glyph glyph = new StubGlyph();


	static final Tixel tixel = new Tixel('X', null);
	static final Tixel emptyTixel = new Tixel(' ', null);
    private boolean gotRedraw;

    @Test
	public void shouldPlaceTixelAtPosition() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		Position position = new Position(3,4);
		
		view.setPosition$To(position, tixel);
		
		assertEquals(view.getTixelAt(position), tixel);
	}

	@Test
	public void shouldIgnorePlacementsOutsideClipRegion() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		Position position = new Position(103,104);
		
		view.setPosition$To(position, tixel);
		
		assertEquals(view.getTixelAt(position), emptyTixel);
	}

	@Test
	public void shouldAcceptClipRegionThatDontStartAtZero() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100,50,50), emptyTixel);
		Position position = new Position(103,104);
		
		view.setPosition$To(position, tixel);
		
		assertEquals(view.getTixelAt(position), tixel);
	}
	
	@Test
	public void shouldWriteContentsToAnotherViewBuffer() {
		ViewBuffer view = new ViewBuffer(new Rectangle(5,5), emptyTixel);
		view.setPosition$To(new Position(0,0), tixel);
		view.setPosition$To(new Position(1,1), tixel);
		
		ViewBuffer target = new ViewBuffer(new Rectangle(5, 5), emptyTixel);
		
		view.writeContentsTo(target);
		
		assertEquals(target.getTixelAt(new Position(0,0)), tixel);
		assertEquals(target.getTixelAt(new Position(1,1)), tixel);
	}
	
	@Test
	public void shouldCopeWithWidthsShorterThanHeights() {
		ViewBuffer view = new ViewBuffer(new Rectangle(15,5), emptyTixel);
		view.setPosition$To(new Position(0,0), tixel);
		view.setPosition$To(new Position(1,1), tixel);
		
		ViewBuffer target = new ViewBuffer(new Rectangle(15, 5), emptyTixel);
		
		view.writeContentsTo(target);
		
		assertEquals(target.getTixelAt(new Position(0,0)), tixel);
		assertEquals(target.getTixelAt(new Position(1,1)), tixel);
	}
	
	@Test
	public void shouldCombineViewsEffortlessly() {
		final Position expectedTixelPosition = new Position(51,51);

		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(10,10), tixel);
		view.setPosition$To(new Position(50,50), tixel);
		view.setPosition$To(new Position(90,90), tixel);
		
		View tranformedView = view.offsetBy(new Position(1,1)).clipTo(new Rectangle(20,20,50,85)).offsetBy(new Position(-2,-2));
		
		assertEquals(tixel, tranformedView.getTixelAt(expectedTixelPosition));
		
		tranformedView.forEachElementDo(new ElementAction() {

			@Override
			public void action(Position position, Tixel foundTixel) {
				if(position.equals(expectedTixelPosition)) {
					if(foundTixel == tixel) {
						// all good
					} else {
						fail(String.format("didn't find tixel at expected position %s", position));
					}
				} else {
					if(foundTixel == tixel) {
						fail(String.format("found tixel at unexpected position %s", position));
					} else {
						// all good
					}
				}
			}
			
		});

	}
    
	@Test
	public void shouldCombineViewsEffortlesslyOnWayDownToo() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);

		View tranformedView = view.offsetBy(new Position(1,1)).clipTo(new Rectangle(20,20,50,85)).offsetBy(new Position(-2,-2));

        assertEquals(tranformedView.getClip(), new Rectangle(22,22,50,79));

        tranformedView.setPosition$To(new Position(21,21), tixel);
        assertEquals(view.getTixelAt(new Position(20,20)), emptyTixel);

        tranformedView.setPosition$To(new Position(22,22), tixel);
        assertEquals(view.getTixelAt(new Position(21,21)), tixel);

        tranformedView.setPosition$To(new Position(40,100), tixel);
        assertEquals(view.getTixelAt(new Position(39,99)), tixel);

        tranformedView.setPosition$To(new Position(40,101), tixel);
        assertEquals(view.getTixelAt(new Position(39,100)), emptyTixel);
	}
	
	@Test
	public void shouldReturnListOfAllDirtyTixelAndClearDirtyTixels() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(10,10), tixel);
		view.setPosition$To(new Position(50,50), tixel);
		view.setPosition$To(new Position(90,90), tixel);
		view.setPosition$To(new Position(50,50), tixel);
		
		Set<Position> dirtyElements = view.getDirtyElementsAndClearThem();
		
		assertEquals(dirtyElements.size(), 3);
		assertTrue(dirtyElements.contains(new Position(10,10)));
		assertTrue(dirtyElements.contains(new Position(50,50)));
		assertTrue(dirtyElements.contains(new Position(90,90)));
		
		dirtyElements = view.getDirtyElementsAndClearThem();
		assertEquals(dirtyElements.size(), 0);
	}
	
	@Test
	public void shouldNotUpdateDirtIfWritingSameThingToView() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(10,10), tixel);
		
		view.getDirtyElementsAndClearThem();
		
		view.setPosition$To(new Position(10,10), tixel);

		assertEquals(view.getDirtyElementsAndClearThem().size(), 0);
	}

    @Test
    public void shouldReturnClipRegion() {
        ViewBuffer view = new ViewBuffer(new Rectangle(5,6,7,8), emptyTixel);

        assertEquals(view.getClip(), new Rectangle(5,6,7,8));
    }

    @Test
    public void shouldInformListenersOfRedrawRequest() {
        ViewBuffer view = new ViewBuffer(new Rectangle(5,6,7,8), emptyTixel);
        gotRedraw = false;
        view.listenForRedraw(new RedrawListener() {
            @Override
            public void gotRedraw() {
                gotRedraw = true;
            }
        });

        view.redraw();

        assertTrue(gotRedraw);
    }
}
