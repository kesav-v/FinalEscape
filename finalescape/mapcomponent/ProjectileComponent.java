package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

import java.awt.Color;

/**
 * A {@link MapComponent} for projectiles, with some basic functionality.
 * Projectiles move in a specific {@link Direction} until they hit something
 * solid. Then they, destroy both the solid {@code MapComponent}, and themselves.
 * These properties can be overridden.
 *
 * All projectiles have corresponding {@link Item}s, which have been
 * {@link finalescape.item.ProjectileItem}s historically.
 *
 * @author Ofek Gila
 * @see MapComponent
 * @see FrisbeeProjectile
 * @see BoomerangProjectile
 * @see PacketProjectile
 * @see finalescape.item.ProjectileItem
 */
public abstract class ProjectileComponent extends MapComponent {

	private Item item;
	private Direction dir;

	/**
	 * Initializes a {@code ProjectileComponent} for a projectile in a {@link Map}.
	 * @param  map  the {@link Map} to add to
	 * @param  x    x coordinate
	 * @param  y    y coordinate
	 * @param  item corresponding {@link Item}
	 * @param  dir  {@link Direction} facing
	 */
	public ProjectileComponent(Map map, int x, int y, Item item, Direction dir) {
		super(map, x, y, item.getName());
		this.item = item;
		this.dir = dir;
		setColor(new Color(0, 0, 0, 0));
		setSolid(true);
		setPrecedence(5);
		setDelayInterval(5);
	}

	/**
	 * The constructor for {@code ProjectileComponent}s outside of a {@link Map}.
	 * @param  item corresponding {@link Item}
	 */
	public ProjectileComponent(Item item) {
		super(item.getName());
		this.item = item;
		setColor(new Color(0, 0, 0, 0));
		setSolid(true);
		setPrecedence(5);
		setDelayInterval(5);
	}

	/**
	 * Gets the {@link Direction} that this component is facing
	 * @return the faced {@link Direction}
	 */
	public Direction getDirection() { return dir; }

	/**
	 * Sets the {@link Direction} for this {@code ProjectileComponent}
	 * @param dir the {@link Direction} to face
	 */
	public void setDirection(Direction dir) { this.dir = dir; }

	/**
	 * Tries moving towards faced {@link Direction} if possible.
	 * If not, destroy. Can be overridden.
	 */
	@Override
	public void tick() {
		int newx = getX() + dir.dX;
		int newy = getY() + dir.dY;
		if (canMoveHere(newx, newy))
			moveTo(newx, newy);
		else destroy();
	}

	/**
	 * Returns true if can move to specific coordinates, false otherwise
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if can move there, false otherwise
	 */
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		componentThere.destroy();
		return false;
	}

	/**
	 * Returns the corresponding {@link Item} for this {@code ProjectileComponent}.
	 * @return the corresponding {@link Item}
	 */
	public Item getItem() { return item; }
}