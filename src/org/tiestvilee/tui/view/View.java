package org.tiestvilee.tui.view;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class View implements Serializable {
    public final Tixel emptyTixel;
    private ArrayList<RedrawListener> redrawListeners;

    protected View(Tixel emptyTixel) {
        this.emptyTixel = emptyTixel;
        redrawListeners = new ArrayList<RedrawListener>();
    }

    public abstract void setPosition$To(Position position, Tixel tixel);

    public void clearPositionAt(Position position) {
        setPosition$To(position, emptyTixel);
    }

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

    public void redraw() {
        for (RedrawListener listener : redrawListeners) {
            listener.gotRedraw();
        }
    }

    public void listenForRedraw(RedrawListener redrawListener) {
        redrawListeners.add(redrawListener);
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
