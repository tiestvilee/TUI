package org.tiestvilee.tui.view;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.ViewBuffer;

public class ViewClippingTest {

	static final Tixel tixel = new Tixel(null, null, null);
	static final Tixel emptyTixel = new Tixel(null, null, null);

	@Test
	public void shouldClipExistingTixels() {
		// Given
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(1,1), tixel);
		view.setPosition$To(new Position(2,2), tixel);
		view.setPosition$To(new Position(3,3), tixel);
		
		// When
		View underlyingView = view.clipTo(new Rectangle(2,2,1,1));
		
		assertEquals(underlyingView.getTixelAt(new Position(1,1)), emptyTixel);
		assertEquals(underlyingView.getTixelAt(new Position(2,2)), tixel);
		assertEquals(underlyingView.getTixelAt(new Position(3,3)), emptyTixel);
	}
	
	@Test
	public void shouldOffsetNewTixels() {
		// Given
		ViewBuffer underlyingView = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		View clippedView = underlyingView.clipTo(new Rectangle(2,2,1,1));
		
		// When
		clippedView.setPosition$To(new Position(1,1), tixel);
		clippedView.setPosition$To(new Position(2,2), tixel);
		clippedView.setPosition$To(new Position(3,3), tixel);
		
		// Then
		assertEquals(underlyingView.getTixelAt(new Position(1,1)), emptyTixel);
		assertEquals(underlyingView.getTixelAt(new Position(2,2)), tixel);
		assertEquals(underlyingView.getTixelAt(new Position(3,3)), emptyTixel);
	}
	
	@Test
	public void shouldCopyFromUnderlyingView() {
		// Given
		ViewBuffer underlyingView = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		underlyingView.setPosition$To(new Position(1,1), tixel);
		underlyingView.setPosition$To(new Position(2,2), tixel);
		underlyingView.setPosition$To(new Position(3,3), tixel);

		View clippedView = underlyingView.clipTo(new Rectangle(2,2,1,1));
		
		View view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		
		// When
		clippedView.writeContentsTo(view);
		
		// Then
		assertEquals(view.getTixelAt(new Position(1,1)), emptyTixel);
		assertEquals(view.getTixelAt(new Position(2,2)), tixel);
		assertEquals(view.getTixelAt(new Position(3,3)), emptyTixel);
	}

}
