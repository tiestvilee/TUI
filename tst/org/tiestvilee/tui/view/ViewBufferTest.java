package org.tiestvilee.tui.view;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;
import org.tiestvilee.tui.view.View.ElementAction;
import org.tiestvilee.tui.view.ViewBuffer;

public class ViewBufferTest {

	static final Tixel tixel = new Tixel(null, null);
	static final Tixel emptyTixel = new Tixel(null, null);

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
		
		View tranformedView = view.offsetBy(new Position(1,1)).clipTo(new Rectangle(20,20,50,50)).offsetBy(new Position(-2,-2));
		
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
}
