package finalescape.item;

import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.PacketProjectile;

public class Packet extends ProjectileItem {

	public Packet() {
		super("Packet");
		setProjectile(new PacketProjectile(this));
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid())
			overrideDestroy(false);
		return false;
	}
}