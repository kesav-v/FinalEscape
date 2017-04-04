public class Boomerang extends ProjectileItem {

	public Boomerang(int uses) {
		super("Boomerang");
		setProjectile(new BoomerangProjectile(this));
		setUses(uses);
		setMaxUses(10);
	}

	public Boomerang() {
		this(0);
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid()) {
			component.destroy();
			incrementUses();
			if (getUses() < maxUses())
				overrideDestroy(false);
			else overrideDestroy(true);
		}
		return false;
	}
}