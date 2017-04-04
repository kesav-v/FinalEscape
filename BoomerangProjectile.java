public class BoomerangProjectile extends ProjectileComponent {

	private boolean bounced;
	private int uses;

	public BoomerangProjectile(Map map, int x, int y, Direction dir, int uses) {
		super(map, x, y, "Boomerang", dir);
		this.uses = uses;
		bounced = false;
		setPrecedence(6);
	}

	public BoomerangProjectile(int uses) {
		super("Boomerang");
		this.uses = uses;
		bounced = false;
		setPrecedence(6);
	}

	private void bounceBack() {
		setDirection(Direction.getDir(getDirection().compassDirection + 180));
		bounced = true;
	}

	@Override
	public void destroy() {
		if (bounced)
			super.destroy();
		else bounceBack();
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		else if (componentThere instanceof Character && bounced) {
			if (uses < 10)
				((Character)componentThere).getInventory().add(new Boomerang(uses + 1));
		} else componentThere.destroy();
		return false;
	}
}