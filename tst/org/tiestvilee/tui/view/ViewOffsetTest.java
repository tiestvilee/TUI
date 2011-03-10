package org.tiestvilee.tui.view;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.ViewBuffer;

public class ViewOffsetTest {

	static final Tixel tixel = new Tixel(null, null);
	static final Tixel emptyTixel = new Tixel(null, null);

	@Test
	public void shouldOffsetExistingTixels() {
		// Given
		ViewBuffer view = new ViewBuffer(new Rectangle(100,100), emptyTixel);
		view.setPosition$To(new Position(3,3), tixel);
		
		// When
		View offsetView = view.offsetBy(new Position(1,1));
		
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
}
