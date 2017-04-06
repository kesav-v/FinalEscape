package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;
import finalescape.item.Item;

import java.awt.Color;

public abstract class ProjectileComponent extends MapComponent {

	private Item item;
	private Direction dir;

	public ProjectileComponent(Map map, int x, int y, Item item, Direction dir) {
		super(map, x, y, item.getName());
		this.item = item;
		this.dir = dir;
		setColor(new Color(0, 0, 0, 0));
		setSolid(true);
		setPrecedence(5);
		setDelayInterval(5);
	}

	public ProjectileComponent(Item item) {
		super(item.getName());
		this.item = item;
		setColor(new Color(0, 0, 0, 0));
		setSolid(true);
		setPrecedence(5);
		setDelayInterval(5);
	}

	public Direction getDirection() { return dir; }
	public void setDirection(Direction dir) { this.dir = dir; }

	@Override
	public void tick() {
		int newx = getX() + dir.dX;
		int newy = getY() + dir.dY;
		if (canMoveHere(newx, newy))
			moveTo(newx, newy);
		else destroy();
	}

	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		if (componentThere == null || !componentThere.isSolid())
			return true;
		componentThere.destroy();
		return false;
	}

	public Item getItem() { return item; }
}