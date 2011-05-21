package org.tiestvilee.tui.view;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.*;
import org.tiestvilee.tui.primitives.Rectangle;

public class ViewOffsetTest {

	static final Tixel tixel = new Tixel('X', null);
	static final Tixel emptyTixel = new Tixel(' ', null);
    private boolean didRedraw;

	@Test
	public void shouldOffsetExistingTixels() {
		// Given
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(3,3), tixel);
		
		// When
		View offsetView = view.offsetBy(new Position(1,1));

        // Then
		assertEquals(offsetView.getTixelAt(new Position(2,2)), tixel);
		assertEquals(offsetView.getTixelAt(new Position(3,3)), emptyTixel);
	}
	
	@Test
	public void shouldOffsetNewTixels() {
		// Given
		ViewBuffer originalView = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		View offsetView = originalView.offsetBy(new Position(1,1));
		
		// When
		offsetView.setPosition$To(new Position(2,2), tixel);
		
		// Then
		assertEquals(originalView.getTixelAt(new Position(2,2)), emptyTixel);
		assertEquals(originalView.getTixelAt(new Position(3,3)), tixel);
	}
	
	@Test
	public void shouldCopyFromUnderlyingView() {
		// Given
		ViewBuffer underlyingView = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		View offsetView = underlyingView.offsetBy(new Position(1,1));
		underlyingView.setPosition$To(new Position(2,2), tixel);
		
		View view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		
		// When
		offsetView.writeContentsTo(view);
		
		// Then
		assertEquals(view.getTixelAt(new Position(1,1)), tixel);
		assertEquals(view.getTixelAt(new Position(2,2)), emptyTixel);
	}

    @Test
    public void shouldReturnClipRegion() {
		// Given
		ViewBuffer originalView = new ViewBuffer(new Rectangle(10, 10, 100, 100), emptyTixel);
		View offsetView = originalView.offsetBy(new Position(10,10));

        // Then
        assertEquals(offsetView.getClip(), new Rectangle(0, 0, 100, 100));
    }

    @Test
    public void shouldMutate() {
		// Given
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(3,3), tixel);
        ViewOffset offsetView = (ViewOffset) view.offsetBy(new Position(0,0));

		// When
		offsetView.mutateBy(new Position(1, 1));

        // Then
		assertEquals(offsetView.getTixelAt(new Position(2,2)), tixel);
		assertEquals(offsetView.getTixelAt(new Position(3, 3)), emptyTixel);

    }

    @Test
    public void shouldListenToUnderlyingViewForRedrawEvents() {
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
        ViewOffset offsetView = (ViewOffset) view.offsetBy(new Position(0,0));
        offsetView.listenForRedraw(new RedrawListener() {
            @Override
            public void gotRedraw() {
                didRedraw = true;
            }
        });

        view.redraw();

        assertTrue(didRedraw);
    }
}
