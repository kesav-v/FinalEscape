package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Location;

import java.awt.Color;

public class Helicopter extends MapComponent {
	public Helicopter(Map map, int x, int y, double destroyRadius) {
		super(map, x, y, "Helicopter");
		setSolid(true);
		setOpaque(false);
		setInvincible(true);
		setColor(Color.BLUE);
		destroyInRadius(destroyRadius);
	}

	private void destroyInRadius(double radius) {
		Map map = getMap();
		for (Location loc : map.getOccupiedLocationsInRadius(this, radius))
			if (shouldRemove(map.get(loc.getX(), loc.getY())))
				map.removeComponent(loc.getX(), loc.getY());
	}

	private boolean shouldRemove(MapComponent comp) {
		if (comp == this || comp instanceof Coder || comp instanceof Jon ||
			!comp.isSolid())
			return false;
		return true;
	}
}
