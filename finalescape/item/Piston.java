package finalescape.item;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.mapcomponent.Character;
import finalescape.mapcomponent.Wall;
import finalescape.mapcomponent.MapComponent;

public class Piston extends Item {
	public Piston() {
		super("Piston");
		setUses(0);
		setMaxUses(10);
	}

	@Override
	public boolean onUse(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		Map map = character.getMap();
		MapComponent componentThere = map.get(spawnx, spawny);
		if (componentThere instanceof Wall) {
			spawnx += dir.dX;
			spawny += dir.dY;

			if (destroy(character, spawnx, spawny)) {
				componentThere.moveTo(spawnx, spawny);
				incrementUses();
			} else if (map.removeComponent(componentThere))
				incrementUses();

			if (getUses() < maxUses())
				return false;
			return true;
		} else return false;
	}

	private boolean destroy(Character character, int x, int y) {
		Map map = character.getMap();
		if (x < 0 || x > map.size() - 1 || y < 0 || y > map.size() - 1)
			return false;
		MapComponent there = map.get(x, y);
		if (there == null)
			return true;
		there.destroy();
		there = map.get(x, y);
		return (there == null) || !there.isSolid();
	}
}