package finalescape.item;

/**
 * A {@link ProjectileComponent} that cannot be placed on anything.
 *
 * @author Ofek Gila
 */

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.PacketProjectile;
import finalescape.mapcomponent.Failure;

public class Packet extends ProjectileItem {

	/**
	 * Initializes this {@code Packet}.
	 */
	public Packet() {
		super("Packet");
		setProjectile(new PacketProjectile(this));
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid())
			if (component instanceof Failure)
				component.preventUpdate(10);
			else overrideDestroy(false);
		return false;
	}
}