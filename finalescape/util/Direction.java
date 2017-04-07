package finalescape.util;

/**
 * An {@code enum} containing the 4 directions components can have
 *
 * @author Ofek Gila
 */
public enum Direction {
	NORTH(0, 0, -1), SOUTH(180, 0, 1), EAST(90, 1, 0), WEST(270, -1, 0),
	IN_PLACE(0, 0, 0);

	/**
	 * The compass direction of the {@code enum} in degrees.
	 */
	public final int compassDirection;

	/**
	 * The delta x and y values associated with this direction.
	 */
	public final int dX, dY;

	Direction(int compassDirection, int dX, int dY) {
		this.compassDirection = compassDirection;
		this.dX = dX;
		this.dY = dY;
	}

	/**
	 * Gets a {@code Direction} given specific delta x and y values
	 * @param  dX delta x
	 * @param  dY dleta y
	 * @return    the corresponding {@code Direction}
	 */
	public static Direction getDir(int dX, int dY) {
		for (Direction dir : values())
			if (dir.dX == dX && dir.dY == dY)
				return dir;
		return null;
	}

	/**
	 * Gets a {@code Direction} given specific compass directions.
	 * @param  compassDirection the compass direction to extrapolate from
	 * @return                  the corresponding {@code Direction}
	 */
	public static Direction getDir(int compassDirection) {
		for (Direction dir : values())
			if (dir.compassDirection == compassDirection % 360)
				return dir;
		return null;
	}
}