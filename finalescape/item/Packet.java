package finalescape.item;

import finalescape.mapcomponent.PacketProjectile;

public class Packet extends ProjectileItem {

	public Packet() {
		super("Packet");
		setProjectile(new PacketProjectile(this));
	}
}