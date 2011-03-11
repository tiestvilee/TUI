package org.tiestvilee.tui.view;

import java.util.Set;

import org.tiestvilee.tui.primitives.Position;
import org.tiestvilee.tui.primitives.Rectangle;
import org.tiestvilee.tui.primitives.Tixel;

public class ViewBuffer extends View {

    public final Rectangle clipRect;

    private final Tixel[][] screen;
    private final PositionSet dirtyPositions = new PositionSet();

    public ViewBuffer(Rectangle clipRect, final Tixel emptyTixel) {
        super(emptyTixel);
        this.clipRect = clipRect;
        screen = new Tixel[clipRect.width][clipRect.height];

        for (int x = 0; x < clipRect.width; x++) {
            for (int y = 0; y < clipRect.height; y++) {
                screen[x][y] = emptyTixel;
            }
        }
    }

    public void setPosition$To(Position position, Tixel tixel) {
        if (clipRect.contains(position)) {
            int x = position.x - clipRect.x;
            int y = position.y - clipRect.y;
            if (screen[x][y] != tixel) {
                screen[x][y] = tixel;
                dirtyPositions.putPosition(position);
            }
        }
    }

    public Tixel getTixelAt(Position position) {
        if (clipRect.contains(position)) {
            return screen[position.x - clipRect.x][position.y - clipRect.y];
        }
        return emptyTixel;
    }

    public void forEachElementDo(ElementAction elementAction) {
        for (int i = 0, x = clipRect.x; i < clipRect.width; i++, x++) {
            for (int j = 0, y = clipRect.y; j < clipRect.height; j++, y++) {
                elementAction.action(new Position(x, y), screen[i][j]);
            }
        }
    }

    public Set<Position> getDirtyElementsAndClearThem() {
        return dirtyPositions.getPositionsAndClearThem();
    }

}
