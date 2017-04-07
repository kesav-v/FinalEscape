package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

/**
 * A {@link ProjectileComponent} for the {@link finalescape.item.Boomerang}. This
 * component has 10 uses, and instead of breaking on collision, it bounces back,
 * and can be picked up by a {@link Character} on the bounce back.
 *
 * @author Ofek Gila
 * @see ProjectileComponent
 * @see finalescape.item.Boomerang
 */
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

	/**
	 * Changes {@link Direction} of this {@link ProjectileComponent}, bouncing
	 * back.
	 */
	private void bounceBack() {
		setDirection(Direction.getDir(getDirection().compassDirection + 180));
		bounced = true;
	}

	/**
	 * If the {@code BoomerangProjectile} has not bounced yet, bounce back. Else, destroy it.
	 */
	@Override
	public void destroy() {
		if (bounced) {
			super.destroy();
			bounced = false;
		} else bounceBack();
	}

	/**
	 * Returns true if the {@code BoomerangProjectile} can move to a specific
	 * coordinate, false otherwise. If on the bounce back, try placing a
	 * {@link finalescape.item.Boomerang} in the {@link Character}'s {@link finalescape.mapcomponent.Inventory},
	 * if they have one.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move there, false otherwise
	 */
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