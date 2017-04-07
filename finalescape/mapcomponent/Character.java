package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.util.MazeGenerator;

/**
 * A {@link MapComponent} for components that move. These characters typically
 * either move forward, or turn to face a specific {@link Direction}. These
 * {@code Character}s also contain an {@link Inventory}, or {@link java.util.ArrayList} of
 * {@link finalescape.item.Item}s that they have.
 *
 * @author Ofek Gila
 * @see Inventory
 * @see Teacher
 * @see Coder
 */
public abstract class Character extends MapComponent {

	private Inventory inventory;

	public Character(Map map, int x, int y, String name, int inventoryCapacity) {
		super(map, x, y, name);
		setSolid(true);
		inventory = new Inventory(inventoryCapacity);
		setDelayInterval(20);
	}

	public Character(String name, int inventoryCapacity) {
		super(name);
		setSolid(true);
		inventory = new Inventory(inventoryCapacity);
		setDelayInterval(20);
	}

	/**
	 * Tries moving {@code Character} toward specific x and y coordinates. Only
	 * moves if in one of the 4 {@link Direction}s, and if it isn't facing that
	 * coordinate, then it turns instead.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if moved, false if invalid (both x and y different, which
	 * would mean not in one of the 4 {@link Direction}s, or if turned instead).
	 */
	public boolean moveCharacter(int x, int y) {
		if ((x == getX()) == (y == getY()))
			return false;
		Direction newDirection = getDirectionTowards(x, y);
		if (newDirection == getDirection())
			if (canMoveHere(x, y)) {
				if (getMap().get(x, y) instanceof ItemComponent && !inventory.isFull())
					inventory.add(((ItemComponent)getMap().get(x, y)).getItem());
				moveTo(x, y);
			} else return false;
		else setDirection(newDirection);
		return true;
	}

	/**
	 * Moves this {@code Character} with a specific delta x and delta y. Calls the
	 * {@link #moveCharacter} method.
	 * @param  dx delta x
	 * @param  dy delta y
	 * @return    true if {@link #moveCharacter} returned true, false otherwise
	 */
	public boolean moveCharacterDelta(int dx, int dy) {
		return moveCharacter(getX() + dx, getY() + dy);
	}

	/**
	 * Moves this {@code Character} randomly. 50% chance to try to move forward,
	 * and 50% chance to try to either turn or move forward.
	 * @return true if moved, false otherwise
	 */
	public boolean moveRandomly() {
		if (Math.random() < 0.5)
			return moveForward();
		else return turnMove();
	}

	/**
	 * Tries moving this {@code Character} forward using {@link #moveCharacterDelta}.
	 * @return true if moved forward, false otherwise
	 */
	public boolean moveForward() {
		if (moveCharacterDelta(getDirection().dX, getDirection().dY))
			return true;
		else return turnMove();
	}

	/**
	 * Tries to either turn or move this {@code Character} forward.
	 * @return true if moved, false otherwise
	 */
	public boolean turnMove() {
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

	/**
	 * Gets the {@link Direction} towards a specific coordinate.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   {@link Direction} towards coordinate
	 */
	public Direction getDirectionTowards(int x, int y) {
		if (x == getX())
			if (y > getY())
				return Direction.SOUTH;
			else return Direction.NORTH;
		else if (x > getX())
			return Direction.EAST;
		else return Direction.WEST;
	}

	/**
	 * Sets this {@code Character}'s {@link Direction} given a specific delta x
	 * and delta y.
	 * @param dx delta x
	 * @param dy delta y
	 */
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

	/**
	 * Checks if this {@code Character} can move to a specific coordinate.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if this {@code Character} can move there, false otherwise
	 */
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		return false;
	}

	/**
	 * Tries moving this {@code Character} in a specific {@link Direction}. If it
	 * can't move, it tries moving randomly.
	 * @param dir {@link Direction} to move
	 */
	public void tryMovingInDir(Direction dir) {
		if (dir == Direction.IN_PLACE)
			moveRandomly();
		else if (dir != getDirection())
			setDirection(dir);
		else {
			int newx = getX() + dir.dX;
			int newy = getY() + dir.dY;
			if (canMoveHere(newx, newy))
				moveTo(newx, newy);
			else moveRandomly();
		}
	}

	/**
	 * Returns this {@code Character}'s {@link Inventory}.
	 * @return this {@code Character}'s {@link Inventory}
	 */
	public Inventory getInventory() { return inventory; }

	/**
	 * Use the selected {@link Inventory} item.
	 */
	public void useSelectedItem() {
		if (inventory.size() > 0)
			inventory.useSelectedItem(this);
	}

	/**
	 * Tries spawning an {@link ItemComponent} in this character's place with the
	 * most precedented {@link finalescape.item.Item} in this {@link Inventory}.
	 */
	@Override
	public void destroy() {
		if (inventory.getMostPrecedentedItem() != null)
			getMap().addComponent(new ItemComponent(getMap(), getX(), getY(),
				inventory.getMostPrecedentedItem()));
		else super.destroy();
	}
}