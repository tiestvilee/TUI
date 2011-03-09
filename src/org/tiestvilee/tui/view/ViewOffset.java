package org.tiestvilee.tui.view;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Tixel;

public class ViewOffset extends View {

	private final View underlyingView;
	private final Position offset;

	public ViewOffset(View underlyingView, Position offset, Tixel emptyTixel) {
		super(emptyTixel);
		this.underlyingView = underlyingView;
		this.offset = offset;
	}

	@Override
	public void setPosition$To(Position position, Tixel tixel) {
		underlyingView.setPosition$To(position.offsetBy(offset), tixel);
	}

	@Override
	public Tixel getTixelAt(Position position) {
		return underlyingView.getTixelAt(position.offsetBy(offset));
	}

	@Override
	public void forEachElementDo(final ElementAction elementAction) {
		underlyingView.forEachElementDo(new ElementAction() {
			@Override
			public void action(Position position, Tixel tixel) {
				elementAction.action(position.offsetBy(offset.negate()), tixel);
			}
		});
	}
}
