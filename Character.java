public abstract class Character extends MapComponent {

	private int health;
	private Inventory inventory;

	public Character(Map map, int x, int y, String name, int inventoryCapacity) {
		super(map, x, y, name);
		health = 100;
		setDirection(Direction.EAST);
		inventory = new Inventory(inventoryCapacity);
	}

	public boolean moveCharacter(int dx, int dy) {
		if ((dx == 0) == (dy == 0)) // both 0 or both not 0
			return false;
		int newx = getX() + dx;
		int newy = getY() + dy;
		setMoveDirection(dx, dy);
		if (!canMoveHere(newx, newy))
			return false;
		moveTo(newx, newy, false);
		return true;
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