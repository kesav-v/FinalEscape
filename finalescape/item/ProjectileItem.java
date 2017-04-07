package finalescape.item;

/**
 * An {@link Item} that spawns a {@link ProjectileComponent} when used.
 *
 * @author Ofek Gila
 * @see Frisbee
 * @see ProjectileComponent
 */

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.ProjectileComponent;
import finalescape.mapcomponent.Character;

public abstract class ProjectileItem extends Item {

	private ProjectileComponent projectile;
	private boolean destroy;
	private boolean destroyOverriden;

	/**
	 * Initializes a {@code ProjectileItem} with a specific name
	 * @param  name the name of the item
	 */
	public ProjectileItem(String name) {
		super(name);
		this.projectile = projectile;
		destroyOverriden = false;
	}

	// CALL THIS
	/**
	 * Sets the {@link ProjectileComponent} of this item. All extending classes
	 * should call this in the constructor!
	 * @param projectile the {@link ProjectileComponent} of this item
	 */
	public void setProjectile(ProjectileComponent projectile) {
		this.projectile = projectile;
	}

	/**
	 * Tries placing the {@link ProjectileComponent} in front of {@link Character}
	 * if possible.
	 * @param  character the {@link Character} placing this {@link Item}
	 * @return           true if item is used up, false otherwise
	 */
	@Override
	public boolean onUse(Character character) {
		placeIfPossible(character);
		if (destroyOverriden) {
			destroyOverriden = false;
			return destroy;
		}
		return true;
	}

	/**
	 * If trying to place on a solid {@link MapComponent}, return false, but also
	 * destroy that component (can be overridden).
	 * @param  component the {@link MapComponent} to place on
	 * @return           true if can place on it, false otherwise
	 */
	@Override
	public boolean canPlaceOn(MapComponent component) {
		if (component == null || !component.isSolid())
			return true;
		else if (component.isSolid())
			component.destroy();
		return false;
	}

	/**
	 * Places down the {@link ProjectileComponent} of this {@code ProjectileItem}
	 * at specific coordinates in the {@link Map}.
	 * @param map the {@link Map} to place in
	 * @param x   x coordinate
	 * @param y   y coordinate
	 * @param dir the {@link Direction} to place in
	 */
	@Override
	public void place(Map map, int x, int y, Direction dir) {
		map.addComponent(projectile, x, y);
		projectile.setDirection(dir);
	}

	/**
	 * Overrides whether or not this item gets destroyed in the {@link #onUse}
	 * method.
	 * @param override true to return true, false to return false
	 * @see finalescape.item.Boomerang
	 */
	public void overrideDestroy(boolean override) {
		destroy = override;
		destroyOverriden = true;
	}
}