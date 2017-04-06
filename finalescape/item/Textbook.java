package finalescape.item;

/**
 * An {@link Item} that can reset the uses of a specific {@code Item} in a
 * {@link Character}'s {@link Inventory}.
 */

import finalescape.mapcomponent.Character;

public class Textbook extends Item {

	/**
	 * Initializes this {@code TextBook}.
	 */
	public Textbook() {
		super("Textbook");
	}

	@Override
	public boolean onUse(Character character) {
		for (Item item : character.getInventory())
			if (item.maxUses() > 1 && item.getUses() > 0) {
				item.setUses(0);
				break;
			}
		return true;
	}
}
