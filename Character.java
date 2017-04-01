public abstract class Character extends MapComponent {

	private int health;
	private Inventory inventory;

	public Character(Map map, int x, int y, String name, int inventoryCapacity) {
		super(map, x, y, name);
		health = 100;
		setDirection(Direction.EAST);
		setSolid(true);
		inventory = new Inventory(inventoryCapacity);
	}

	public Character(String name, int inventoryCapacity) {
		super(name);
		health = 100;
		setDirection(Direction.EAST);
		setSolid(true);
		inventory = new Inventory(inventoryCapacity);
	}

	public boolean moveCharacter(int x, int y) {
		if ((x == getX()) == (y == getY()))
			return false;
		Direction newDirection = getDirectionTowards(x, y);
		if (newDirection == getDirection())
			if (canMoveHere(x, y))
				moveTo(x, y);
			else return false;
		else setDirection(newDirection);
		return true;
	}

	public boolean moveCharacterDelta(int dx, int dy) {
		return moveCharacter(getX() + dx, getY() + dy);
	}

	public Direction getDirectionTowards(int x, int y) {
		if (x == getX())
			if (y > getY())
				return Direction.NORTH;
			else return Direction.SOUTH;
		else if (x > getX())
			return Direction.EAST;
		else return Direction.WEST;
	}

	public void setMoveDirection(int dx, int dy) {
		if (dx > 0) // looking to the right
			setDirection(Direction.EAST);
		else if (dx < 0)
			setDirection(Direction.WEST);
		else if (dy > 0)
			setDirection(Direction.NORTH);
		else if (dy < 0)
			setDirection(Direction.SOUTH);
	}

	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		return false;
	}

	public void setHealth(int health) { this.health = health; }
	public int getHealth() { return health; }

	public Inventory getInventory() { return inventory; }
}