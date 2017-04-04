import java.awt.Color;

public class David extends Teacher {

	private int tickCount;

	public David() {
		super("David", new Laptop(), 0);
		setColor(Color.PINK);
	}

	@Override
	public void tick() {
		Character target = getTarget();
		if (target.getInventory().getSelectedItem() instanceof Laptop
			&& distance(target) <= getMap().getVisibilityRadius()) {
			tryMovingInDir(solveMazeDirection(getMap().getDestinationComponent()));
		} else if (distance(target) <= getMap().getVisibilityRadius())
			tryMovingInDir(solveMazeDirection(getMap().findLaptop()));
		else moveRandomly();
	}

	private void tryMovingInDir(Direction dir) {
		if (dir == Direction.IN_PLACE)
			moveRandomly();
		else if (dir != getDirection())
			setDirection(dir);
		else {
			int newx = getX() + dir.dX;
			int newy = getY() + dir.dY;
			if (canMoveHere(newx, newy))
				moveTo(newx, newy);
			else moveRandomly();
		}
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