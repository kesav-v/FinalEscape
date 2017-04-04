package finalescape.item;

import finalescape.mapcomponent.Character;

public class Textbook extends Item {
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