public class Boomerang extends ProjectileItem {

	public int uses;

	public Boomerang(int uses) {
		super("Boomerang", new BoomerangProjectile(uses));
		this.uses = uses;
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
			uses++;
			if (uses < 10)
				overrideDestroy(false);
			else overrideDestroy(true);
		}
		return false;
	}
}