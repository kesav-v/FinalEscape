public class Frisbee extends Item {

	public Frisbee() {
		super("Frisbee");
	}

	@Override
	public boolean onUse(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		MapComponent componentThere = character.getMap().get(spawnx, spawny);
		if (componentThere == null || !componentThere.isSolid())
			new FrisbeeProjectile(character.getMap(), spawnx, spawny, dir);
		else if (componentThere.isSolid())
			componentThere.destroy();
		return true;
	}
}