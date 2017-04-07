package finalescape.mapcomponent;

import finalescape.map.Map;

import java.awt.Color;

/**
 * The main {@link Character} of the {@link Map}, the {@code Coder} is controlled
 * by the player.
 *
 * The goal of the {@code Coder} is to bring the {@link finalescape.item.Laptop}
 * to the {@link Desk}.
 *
 * @author Ofek Gila
 * @see Character
 */
public class Coder extends Character {

	public Coder(Map map, int x, int y) {
		super(map, x, y, "Coder", 5);
		setColor(Color.BLUE);
	}

	@Override
	/**
	 * Checks if can move to coordinate, picking up any {@link finalescape.item.Item}s
	 * from any {@link ItemComponent}s it steps on.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move here, false otherwise
	 */
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		Inventory inventory = getInventory();

		if (componentThere == null)
			return true;
		else if (componentThere instanceof ItemComponent) {
			if (inventory.isFull() && componentThere.getName().equals("Laptop"))
				inventory.set(inventory.getSelectedItemIndex(),
					((ItemComponent)componentThere).getItem());
			return true;
		} else return !componentThere.isSolid();
	}

	@Override
	/**
	 * When the {@code Coder} is destroyed, the game is lost.
	 */
	public void destroy() {
		getMap().loseGame();
	}
}