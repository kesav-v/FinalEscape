public abstract class Character extends MapComponent {

	private int health;
	private Inventory inventory;

	public Character(Map map, int x, int y, String name, int inventoryCapacity) {
		super(map, x, y, name);
		health = 100;
		setSolid(true);
		inventory = new Inventory(inventoryCapacity);
	}

	public Character(String name, int inventoryCapacity) {
		super(name);
		health = 100;
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

	public boolean moveRandomly() {
		int[] ranDs = MazeGenerator.getRanDs(4);
		for (int dir : ranDs)
			switch (dir) {
				case 0:
					if (moveCharacterDelta(0, 1))
						return true;
					break;
				case 1:
					if (moveCharacterDelta(0, -1))
						return true;
					break;
				case 2:
					if (moveCharacterDelta(1, 0))
						return true;
					break;
				case 3:
					if (moveCharacterDelta(-1, 0))
						return true;
					break;
			}
		return false;
	}

	public Direction getDirectionTowards(int x, int y) {
		if (x == getX())
			if (y > getY())
				return Direction.SOUTH;
			else return Direction.NORTH;
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

	public void useSelectedItem() {
		if (inventory.size() > 0)
			inventory.useSelectedItem(this);
	}

	@Override
	public void destroy() {
		if (inventory.getSelectedItem() != null)
			getMap().addComponent(new ItemComponent(getMap(), getX(), getY(),
				inventory.getSelectedItem()));
		else super.destroy();
	}
}