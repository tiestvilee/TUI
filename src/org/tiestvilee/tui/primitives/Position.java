package org.tiestvilee.tui.primitives;

public class Position {

	public final int y;
	public final int x;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position offsetBy(Position offset) {
		return new Position(x + offset.x, y + offset.y);
	}

	public Position negate() {
		return new Position(-x, -y);
	}

	@Override
	public int hashCode() {
		return (x<<16) + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
