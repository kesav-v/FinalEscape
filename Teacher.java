import java.awt.Color;

public class Teacher extends Character {

	private Item itemOfChoice;

	public Teacher(String name, Item itemOfChoice) {
		super(name, 2);
		this.itemOfChoice = itemOfChoice;
		getInventory().add(itemOfChoice);
		setColor(Color.RED);
	}

	public Teacher() {
		this("Teacher", new Textbook());
	}

	@Override
	public void tick() {
		Character target = getTarget();
		if (target == null) {
			moveRandomly();
			return;
		}
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

	public Character getTarget() {
		Character target = getMap().getMainCharacter();
		if (instof(target.getInventory().getSelectedItem(), itemOfChoice))
			return null;
		return target;
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		else if (componentThere == getMap().getMainCharacter()
			&& !(instof(((Character)componentThere).getInventory().getSelectedItem(), itemOfChoice)))
			getMap().getMainCharacter().destroy();
		else if (componentThere instanceof ItemComponent && !getInventory().isFull())
			return true;
		return false;
	}

	public Item getItemOfChoice() { return itemOfChoice; }
}