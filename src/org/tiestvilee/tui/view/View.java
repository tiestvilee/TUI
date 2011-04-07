package org.tiestvilee.tui.view;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;

public abstract class View {
    protected final Tixel emptyTixel;

    protected View(Tixel emptyTixel) {
        this.emptyTixel = emptyTixel;
    }

    public abstract void setPosition$To(Position position, Tixel tixel);

    public abstract Tixel getTixelAt(Position position);

    public abstract void forEachElementDo(ElementAction elementAction);

    public abstract Rectangle getClip();

    public void writeContentsTo(final View target) {
        forEachElementDo(new ElementAction() {
            @Override
            public void action(Position position, Tixel tixel) {
                target.setPosition$To(position, tixel);
            }
        });
    }

    public View offsetBy(Position position) {
        return new ViewOffset(this, position, emptyTixel);
    }

    public View clipTo(Rectangle clipRect) {
        return new ViewClipping(this, clipRect, emptyTixel);
    }

    public interface ElementAction {
        public void action(Position position, Tixel tixel);
    }

}
