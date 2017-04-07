package finalescape.mapcomponent;

import finalescape.util.Direction;
import finalescape.item.Laptop;

import java.awt.Color;

/**
 * The only {@link Teacher} that isn't out to kill the {@link Coder}. {@code David}
 * shows the {@code Coder} the path to the {@link Laptop}, and then to the {@link Desk}.
 *
 * @author Ofek Gila
 * @see Teacher
 */
public class David extends Teacher {

	public David() {
		super("David", new Laptop(), 0);
		setColor(Color.PINK);
	}

	/**
	 * If the {@link Coder} is close by, if they have the {@link Laptop} selected,
	 * lead them to the {@link Desk}. If they don't, lead them to the {@link Laptop}.
	 *
	 * If the {@code Coder} isn't within viewing distance, move randomly
	 */
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

	/**
	 * Checks if it can move to a specific coordinate, without picking up any
	 * {@link Laptop}, but picking up other {@link finalescape.item.Item}s.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move there, false otherwise
	 */
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

	/**
	 * Returns the main {@link Character} as the target, whether or not they are
	 * holding the item of choice.
	 * @return the {@link Character} to target
	 */
	@Override
	public Character getTarget() {
		return getMap().getMainCharacter();
	}
}