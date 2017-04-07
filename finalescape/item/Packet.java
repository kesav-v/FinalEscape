package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.PacketProjectile;
import finalescape.mapcomponent.Failure;

/**
 * A {@link finalescape.mapcomponent.ProjectileComponent} that cannot be placed
 * on anything. The main {@link Item} of {@link finalescape.mapcomponent.Kavita}.
 *
 * @author Ofek Gila
 * @see PacketProjectile
 * @see finalescape.mapcomponent.Kavita
 */
public class Packet extends ProjectileItem {

	/**
	 * Initializes this {@code Packet}.
	 */
	public Packet() {
		super("Packet");
		setProjectile(new PacketProjectile(this));
	}

	/**
	 * Checks if a {@code Packet} can be placed at a specific coordinate, or stuns
	 * {@link Failure} for a while.
	 * @param  component {@link MapComponent} to place on
	 * @return           true if can be placed on, false otherwise
	 */
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