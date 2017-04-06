package finalescape.mapcomponent;

import finalescape.util.Direction;
import finalescape.item.Laptop;

import java.awt.Color;

public class David extends Teacher {

	public David() {
		super("David", new Laptop(), 0);
		setColor(Color.PINK);
	}

	@Override
	public void tick() {
		Character target = getTarget();
		if (target.getInventory().getSelectedItem() instanceof Laptop
			&& distance(target) <= getMap().getVisibilityRadius()) {
			if (distance(getMap().getDestinationComponent()) <= getMap().getVisibilityRadius())
				moveRandomly();
			else tryMovingInDir(solveMazeDirection(getMap().getDestinationComponent()));
		} else if (distance(target) <= getMap().getVisibilityRadius())
			if (distance(getMap().findLaptop()) <= getMap().getVisibilityRadius())
				moveRandomly();
			else tryMovingInDir(solveMazeDirection(getMap().findLaptop()));
		else moveRandomly();
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null)
			return true;
		else if (componentThere instanceof ItemComponent
			&& !getInventory().isFull() && !componentThere.getName().equals("Laptop"))
			return true;
		return false;
	}

	@Override
	public Character getTarget() {
		return getMap().getMainCharacter();
	}
}