import java.awt.Color;

public class Teacher extends Character {

	public Teacher() {
		super("Teacher", 1);
		setColor(Color.RED);
		getInventory().add(new Textbook());
	}

	@Override
	public void tick() {
		Character target = getMap().getMainCharacter();
		if (target.getX() == getX())
			if (target.getY() > getY())
				moveCharacterDelta(0, 1);
			else moveCharacterDelta(0, -1);
		else if (target.getY() == getY())
			if (target.getX() > getX())
				moveCharacterDelta(1, 0);
			else moveCharacterDelta(-1, 0);
		else moveRandomly();
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		else if (componentThere == getMap().getMainCharacter())
			getMap().getMainCharacter().destroy();
		return false;
	}
}