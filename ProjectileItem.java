public class ProjectileItem extends Item {

	private ProjectileComponent projectile;
	private boolean destroy;
	private boolean destroyOverriden;

	public ProjectileItem(String name, ProjectileComponent projectile) {
		super(name);
		this.projectile = projectile;
		destroyOverriden = false;
	}

	@Override
	public boolean onUse(Character character) {
		placeIfPossible(character);
		if (destroyOverriden) {
			destroyOverriden = false;
			return destroy;
		}
		return true;
	}

	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid())
			component.destroy();
		return false;
	}

	@Override
	public void place(Map map, int x, int y, Direction dir) {
		map.addComponent(projectile, x, y);
		projectile.setDirection(dir);
	}

	public void overrideDestroy(boolean override) {
		destroy = override;
		destroyOverriden = true;
	}
}