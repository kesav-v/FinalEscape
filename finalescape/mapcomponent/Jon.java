package finalescape.mapcomponent;

import finalescape.util.Location;
import finalescape.util.Direction;
import finalescape.item.Gavel;

import java.util.ArrayList;

public class Jon extends Teacher {

	private final double visibilityRadius = 3.2;
	private Helicopter helicopter;
	private boolean helicopterSpawned;

	public Jon() {
		super("Jon", new Gavel(), 1, 1);
		setDelayInterval(10);
		helicopter = null;
		helicopterSpawned = false;
	}

	@Override
	public void tick() {
		Character target = getTarget();
		boolean hasGavel = getInventory().getSelectedItem() instanceof Gavel;
		if (!helicopterSpawned)
			trySpawningHelicopter();
		if (!helicopterSpawned || target == null) {
			moveRandomly();
			return;
		} else if (target.getX() == getX() && hasGavel)
			if (target.getY() > getY())
				setDirection(Direction.getDir(0, 1));
			else setDirection(Direction.getDir(0, -1));
		else if (target.getY() == getY() && hasGavel)
			if (target.getX() > getX())
				setDirection(Direction.getDir(1, 0));
			else setDirection(Direction.getDir(-1, 0));
		else {
			if (helicopter.distance(target) <= visibilityRadius) {
				setDelayInterval(5);
				tryMovingInDir(solveMazeDirection(target));
			} else {
				setDelayInterval(10);
				if (distance(helicopter) > visibilityRadius)
					tryMovingInDir(solveMazeDirection(helicopter));
				else moveRandomly();
			}
			if (Math.random() < 0.25)
				getInventory().add(new Gavel());
			return;
		}
		getInventory().useSelectedItem(this);
	}

	private void trySpawningHelicopter() {
		ArrayList<Location> possibleLocations = getMap().getEmptyLocationsInRadius(
			this, visibilityRadius);
		if (possibleLocations.size() > 0) {
			Location spawnLoc = possibleLocations.get((int)(Math.random() *
				possibleLocations.size()));
			helicopter = new Helicopter(getMap(), spawnLoc.getX(), spawnLoc.getY(),
				visibilityRadius);
			helicopterSpawned = true;
		}
	}
}