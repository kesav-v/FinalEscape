package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.PacketProjectile;
import finalescape.mapcomponent.Failure;

/**
 * A {@link finalescape.mapcomponent.ProjectileComponent} that cannot be placed on anything.
 *
 * @author Ofek Gila
 * @see PacketProjectile
 */
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