package finalescape.mapcomponent;

/**
 * A genetic {@link ProjectileComponent}, corresponding to the {@link finalescape.item.Frisbee}
 * {@link Item}.
 *
 * @author Ofek Gila
 * @see ProjectileComponent
 * @see finalescape.item.Frisbee
 */

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

public class FrisbeeProjectile extends ProjectileComponent {
	public FrisbeeProjectile(Map map, int x, int y, Direction dir, Item item) {
		super(map, x, y, item, dir);
	}

	public FrisbeeProjectile(Item item) {
		super(item);
	}
}