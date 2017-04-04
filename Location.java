public class Location {

	private final int x, y;
	private Direction direction;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }

	public void setDirection(Direction dir) { direction = dir; }
	public Direction getDirection() { return direction; }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
			return false;
		Location loc = (Location)obj;
		return loc.x == x && loc.y == y;
	}
}