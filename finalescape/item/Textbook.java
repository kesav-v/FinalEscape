package finalescape.item;

import finalescape.mapcomponent.Character;

/**
 * An {@link Item} that can reset the uses of a specific {@code Item}
 * in a {@link Character}'s {@link finalescape.mapcomponent.Inventory}. The main
 * {@code Item} of a {@link finalescape.mapcomponent.Teacher}.
 *
 * @author Ofek Gila
 * @see Item
 * @see finalescape.mapcomponent.Teacher
 */
public class Textbook extends Item {

	/**
	 * Initializes this {@code TextBook}.
	 */
	public Textbook() {
		super("Textbook");
	}

	/**
	 * Finds the most used {@link Item} in the {@link Character}'s {@link finalescape.mapcomponent.Inventory},
	 * and resets its uses.
	 * @param  character the {@link Character} holding this {@code Textbook}
	 * @return           true since used up
	 */
	@Override
	public boolean onUse(Character character) {
		int maxUses = 0;
		Item mostUsedItem = null;
		for (Item item : character.getInventory())
			if (item.getUses() > maxUses) {
				mostUsedItem = item;
				maxUses = mostUsedItem.getUses();
			}
		if (mostUsedItem != null)
			mostUsedItem.setUses(0);
		return true;
	}
}
