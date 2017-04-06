package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

public class BoomerangProjectile extends ProjectileComponent {

	private boolean bounced;

	public BoomerangProjectile(Map map, int x, int y, Direction dir, Item item) {
		super(map, x, y, item, dir);
		bounced = false;
		setPrecedence(6);
	}

	public BoomerangProjectile(Item item) {
		super(item);
		bounced = false;
		setPrecedence(6);
	}

	private void bounceBack() {
		setDirection(Direction.getDir(getDirection().compassDirection + 180));
		bounced = true;
	}

	@Override
	public void destroy() {
		if (bounced) {
			super.destroy();
			bounced = false;
		} else bounceBack();
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		else if (componentThere instanceof Character && bounced) {
			Item item = getItem();
			item.incrementUses();
			if (item.getUses() <= item.maxUses())
				((Character)componentThere).getInventory().add(item);
		} else componentThere.destroy();
		return false;
	}
}