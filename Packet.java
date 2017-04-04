public class Packet extends ProjectileItem {

	public Packet() {
		super("Packet");
		setProjectile(new PacketProjectile(this));
	}
}