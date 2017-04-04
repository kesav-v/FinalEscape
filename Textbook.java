public class Textbook extends Item {
	public Textbook() {
		super("Textbook");
	}

	@Override
	public boolean onUse(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		MapComponent componentThere = character.getMap().get(spawnx, spawny);
		if (componentThere == null)
			new ItemComponent(character.getMap(), spawnx, spawny, this);
		return true;
	}
}