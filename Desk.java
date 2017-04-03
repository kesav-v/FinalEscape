import java.awt.Color;

public class Desk extends MapComponent {
	public Desk(Map map, int x, int y, String name) {
		super(map, x, y, name);
		setColor(new Color(139, 69, 19));
		if (x == 0)
			setDirection(Direction.WEST);
		else if (x == map.size() - 1)
			setDirection(Direction.EAST);
		else if (y == map.size() - 1)
			setDirection(Direction.SOUTH);
		else setDirection(Direction.NORTH);
	}

	public Desk(Map map, int x, int y) {
		this(map, x, y, "Desk");
	}
}