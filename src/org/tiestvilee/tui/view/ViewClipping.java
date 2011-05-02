package org.tiestvilee.tui.view;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;

public class ViewClipping extends View implements RedrawListener {

    private final View underlyingView;
    private final Rectangle clipRegion;

    public ViewClipping(View underlyingView, Rectangle clipRegion, Tixel emptyTixel) {
        super(emptyTixel);
        this.underlyingView = underlyingView;
        this.clipRegion = clipRegion;
        underlyingView.listenForRedraw(this);
    }

    @Override
    public void setPosition$To(Position position, Tixel tixel) {
        if (clipRegion.contains(position)) {
            underlyingView.setPosition$To(position, tixel);
        }
    }

    @Override
    public Tixel getTixelAt(Position position) {
        if (clipRegion.contains(position)) {
            return underlyingView.getTixelAt(position);
        }
        return emptyTixel;
    }

    @Override
    public void forEachElementDo(final ElementAction elementAction) {
        underlyingView.forEachElementDo(new ElementAction() {
            @Override
            public void action(Position position, Tixel tixel) {
                if (clipRegion.contains(position)) {
                    elementAction.action(position, tixel);
                }
            }
        });
    }

    @Override
    public Rectangle getClip() {
        return clipRegion.intersectWith(underlyingView.getClip());
    }

    @Override
    public void gotRedraw() {
        redraw();
    }
}
