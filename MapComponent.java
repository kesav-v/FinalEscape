import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import java.awt.Color;

public abstract class MapComponent {

	private final String name;

	private int x, y;
	private Map map;
	private boolean solid, opaque;
	private Image img;
	private Direction direction;
	private Color color;
	private int precedence;
	private int delayInterval;

	public MapComponent(Map map, int x, int y, String name) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.name = name;
		map.addComponent(this);
		setDefaults();
	}

	public MapComponent(String name) {
		this.name = name;
		setDefaults();
	}

	private void setDefaults() {
		direction = Direction.NORTH;
		solid = opaque = false;
		color = Color.GRAY;
		img = getImageByName(name);
		precedence = 0;
		delayInterval = 5;
	}

	public final void moveTo(int x, int y) {
		map.moveComponent(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
	}

	public static Image getImageByName(String name) {
		Image img = null;
		try {
			img = ImageIO.read(new File("images/" + name + ".png"));
		} catch (IOException e) {
			System.err.printf("Failed to load image in path: %s", "images/" + name + ".png");
			System.exit(1);
		}
		return img;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public Map getMap() { return map; }
	public Image getImage() { return img; }
	public Direction getDirection() { return direction; }
	public Color getColor() { return color; }
	public String getName() { return name; }
	public int getPrecedence() { return precedence; }

	public boolean isSolid() { return solid; }
	public boolean isOpaque() { return opaque; }
	public int getDelayInterval() { return delayInterval; }

	public void setSolid(boolean solid) { this.solid = solid; }
	public void setOpaque(boolean opaque) { this.opaque = opaque; }
	public void setDirection(Direction dir) { direction = dir; }
	public void setColor(Color color) { this.color = color; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setMap(Map map) { this.map = map; }
	public void setPrecedence(int precedence) { this.precedence = precedence; }
	public void setDelayInterval(int interval) { delayInterval = interval; }

	public void tick() {}

	public final boolean instof(Object obj1, Object obj2) {
		if (obj1 == null)
			return false;
		return obj2.getClass().isAssignableFrom(obj1.getClass());
	}

	@Override
	public String toString() {
		if (map != null)
			return String.format("[MapComponent %s in %d % d]", name, x, y);
		else return String.format("[MapComponent %s out of map]", name);
	}

	public void destroy() {
		map.removeComponent(this);
	}

	public double distance(MapComponent that) {
		return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
	}

	public Direction solveMazeDirection(MapComponent componentTo) {
		return map.solveMazeDirection(this, componentTo);
	}
}