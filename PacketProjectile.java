public class PacketProjectile extends ProjectileComponent {
	public PacketProjectile(Map map, int x, int y, Direction dir) {
		super(map, x, y, "Packet", dir);
	}

	public PacketProjectile() {
		super("Packet");
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