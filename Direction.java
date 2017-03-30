public enum Direction {
	NORTH(0), SOUTH(180), EAST(90), WEST(270);

	public final int compassDirection;

	Direction(int compassDirection) {
		this.compassDirection = compassDirection;
	}
}