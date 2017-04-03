import java.awt.Color;

public abstract class ProjectileComponent extends MapComponent {

	private Direction dir;

	public ProjectileComponent(Map map, int x, int y, String name, Direction dir) {
		super(map, x, y, name);
		this.dir = dir;
		setColor(Color.PINK);
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
}