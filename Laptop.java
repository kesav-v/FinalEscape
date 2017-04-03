import java.awt.Color;

public class Laptop extends Item {
	public Laptop() {
		super("Laptop");
		setColor(new Color(212, 175, 55)); // gold
		setPrecedence(Integer.MAX_VALUE);
	}

	@Override
	public boolean onUse(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		MapComponent componentThere = character.getMap().get(spawnx, spawny);
		if (componentThere == null || !componentThere.isSolid()) {
			new ItemComponent(character.getMap(), spawnx, spawny, this);
			return true;
		} else if (componentThere instanceof Desk) {
			character.getMap().addComponent(
				new DeskWithLaptop(character.getMap(), spawnx, spawny), true);
			// win the game
			return true;
		} else return false;
	}
}