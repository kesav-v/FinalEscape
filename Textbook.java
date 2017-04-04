public class Textbook extends Item {
	public Textbook() {
		super("Textbook");
	}

	@Override
	public boolean onUse(Character character) {
		for (Item item : character.getInventory())
			if (item.maxUses() > 1 && item.getUses() < item.maxUses()) {
				item.setUses(item.maxUses());
				break;
			}
		return true;
	}
}