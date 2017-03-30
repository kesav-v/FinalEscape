public class Location {

	private final int x, y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
			return false;
		Location loc = (Location)obj;
		return loc.x == x && loc.y == y;
	}
}