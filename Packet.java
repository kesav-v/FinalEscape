public class Packet extends Item {

	public Packet() {
		super("Packet");
	}

	@Override
	public boolean onUse(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		MapComponent componentThere = character.getMap().get(spawnx, spawny);
		if (componentThere == null || !componentThere.isSolid())
			new PacketProjectile(character.getMap(), spawnx, spawny, dir);
		return true;
	}
}