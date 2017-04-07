package finalescape.util;


/**
 * A helper method used typically in Lists, for storing both X and Y values
 * in one {@code Object}, and having an equals method.
 *
 * @author Ofek Gila
 */
public class Location {

	private final int x, y;
	private Direction direction;

	/**
	 * Initializes a {@code Location} with a specific coordinate.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of this {@code Location}
	 * @return the x coordinate of this {@code Location}
	 */
	public int getX() { return x; }

	/**
	 * Returns the y coordinate of this {@code Location}
	 * @return the y coordinate of this {@code Location}
	 */
	public int getY() { return y; }

	/**
	 * Sets the {@link Direction} of this {@code Location} (for maze solving).
	 * @param dir the {@link Direction} to set
	 * @see finalescape.map.Map#solveMazeDirection
	 */
	public void setDirection(Direction dir) { direction = dir; }

	/**
	 * Gets the {@link Direction} of this {@code Location} (for maze solving).
	 * @return the {@link Direction} of this {@code Location}
	 * @see finalescape.map.Map#solveMazeDirection
	 */
	public Direction getDirection() { return direction; }

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
			return false;
		Location loc = (Location)obj;
		return loc.x == x && loc.y == y;
	}
}