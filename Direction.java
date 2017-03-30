public enum Direction {
	NORTH(180, 0, -1), SOUTH(0, 0, 1), EAST(90, 1, 0), WEST(270, -1, 0);

	public final int compassDirection;
	public final int dX, dY;

	Direction(int compassDirection, int dX, int dY) {
		this.compassDirection = compassDirection;
		this.dX = dX;
		this.dY = dY;
	}
}