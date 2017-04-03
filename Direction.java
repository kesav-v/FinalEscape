public enum Direction {
	NORTH(0, 0, -1), SOUTH(180, 0, 1), EAST(90, 1, 0), WEST(270, -1, 0);

	public final int compassDirection;
	public final int dX, dY;

	Direction(int compassDirection, int dX, int dY) {
		this.compassDirection = compassDirection;
		this.dX = dX;
		this.dY = dY;
	}

	Direction getDir(int dX, int dY) {
		for (Direction dir : values())
			if (dir.dX == dX && dir.dY == dY)
				return dir;
		return null;
	}
}