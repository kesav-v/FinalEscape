package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

public class GavelProjectile extends ProjectileComponent {
	public GavelProjectile(Map map, int x, int y, Direction dir, Item item) {
		super(map, x, y, item, dir);
		setDelayInterval(10);
	}

	public GavelProjectile(Item item) {
		super(item);
		setDelayInterval(10);
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		if (componentThere == getMap().getMainCharacter()
			|| componentThere instanceof ProjectileComponent)
			componentThere.destroy();
		else if (componentThere instanceof Teacher)
			stunAllOfType(getMap(), componentThere);
		return false;
	}

	public static void stunAllOfType(Map map, Object type) {
		for (int i = 0; i < map.size(); i++)
			for (int a = 0; a < map.size(); a++)
				if (instof(type, map.get(i, a)) && instof(map.get(i, a), type))
					map.get(i, a).preventUpdate(10);
	}
}