package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

public class GavelProjectile extends ProjectileComponent {
	public GavelProjectile(Map map, int x, int y, Direction dir, Item item) {
		super(map, x, y, item, dir);
	}

	public GavelProjectile(Item item) {
		super(item);
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		if (componentThere == getMap().getMainCharacter()
			|| componentThere instanceof ProjectileComponent)
			componentThere.destroy();
		return false;
	}
}