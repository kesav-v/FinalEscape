package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.BoomerangProjectile;

/**
 * A {@link ProjectileItem} with a specific number of uses.
 *
 * @author Ofek Gila
 * @see BoomerangProjectile
 */
public class Boomerang extends ProjectileItem {

	/**
	 * Initializes this boomerang with a specific number of uses
	 * @param  uses the number of uses this {@code Boomerang} already has
	 */
	public Boomerang(int uses) {
		super("Boomerang");
		setProjectile(new BoomerangProjectile(this));
		setUses(uses);
		setMaxUses(10);
	}

	/**
	 * Initializes this {@code Boomerang} with no uses.
	 */
	public Boomerang() {
		this(0);
	}

	/**
	 * Returns true if the {@code Boomerang} can be placed on a specific
	 * {@link MapComponent}, false otherwise. If the component is solid,
	 * don't destroy the {@code Boomerang} unless it has no uses left.
	 * @param  component the {@link MapComponent} to place on
	 * @return           true if can place, false otherwise
	 */
	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid()) {
			component.destroy();
			incrementUses();
			if (getUses() < maxUses())
				overrideDestroy(false);
			else overrideDestroy(true);
		}
		return false;
	}
}