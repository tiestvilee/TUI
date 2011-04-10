package org.tiestvilee.tui.primitives;

public class Rectangle {

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }

    public boolean contains(Position position) {
        return position.x >= x && position.x < (x + width) && position.y >= y && position.y < (y + height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (height != rectangle.height) return false;
        if (width != rectangle.width) return false;
        if (x != rectangle.x) return false;
        if (y != rectangle.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
            "x=" + x +
            ", y=" + y +
            ", width=" + width +
            ", height=" + height +
            '}';
    }

    public Rectangle intersectWith(Rectangle clip) {

        if(clip.x + clip.width < x)
            return new Rectangle(0,0);
        if(clip.y + clip.height < y)
            return new Rectangle(0,0);
        if(x + width < clip.x)
            return new Rectangle(0,0);
        if(y + height < clip.y)
            return new Rectangle(0,0);

        int newX = clip.x < x ? x : clip.x;
        int newY = clip.y < y ? y : clip.y;
        int newX2 = (clip.x + clip.width) < (x + width) ? (clip.x + clip.width) : (x + width);
        int newY2 = (clip.y + clip.height) < (y + height) ? (clip.y + clip.height) : (y + height);

        return new Rectangle(newX, newY, newX2-newX, newY2-newY);
    }

    public Rectangle offsetBy(Position offset) {
        return new Rectangle(x + offset.x, y + offset.y, width, height);
    }
}
