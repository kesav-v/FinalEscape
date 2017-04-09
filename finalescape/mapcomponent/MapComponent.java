package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.util.Direction;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import java.awt.Color;

/**
 * The main class for items going in the {@link Map}. Somewhat similar to the
 * {@code Actor} class in GridWorld (kinda).
 *
 * @author Ofek Gila
 * @see Wall
 * @see Teacher
 * @see ItemComponent
 * @see Map
 */
public abstract class MapComponent {

	private final String name;

	private int x, y;
	private Map map;
	private boolean solid, opaque;
	private boolean invincible;
	private BufferedImage img;
	private Direction direction;
	private Color color;
	private int precedence;
	private int delayInterval;
	private int preventUpdate;

	/**
	 * Call this constructor when creating a new {@code MapComponent} to place
	 * in a {@link Map}.
	 * @param  map  the {@link Map} to place in
	 * @param  x    x coordinate
	 * @param  y    y coordinate
	 * @param  name name of the coordinate (should have image file in images/[name].png)
	 */
	public MapComponent(Map map, int x, int y, String name) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.name = name;
		map.addComponent(this);
		setDefaults();
	}

	/**
	 * Call this constructor when creating a new {@code MapComponent} to not
	 * immediately place in a map.
	 * @param  name name of the coordinate (should have image file in images/[name].png)
	 */
	public MapComponent(String name) {
		this.name = name;
		setDefaults();
	}

	/**
	 * Sets some defaults for {@code MapComponent}s
	 */
	private void setDefaults() {
		direction = Direction.NORTH;
		solid = opaque = false;
		color = Color.GRAY;
		precedence = 0;
		delayInterval = 5;
		preventUpdate = 0;
		invincible = false;
	}

	/**
	 * Move this {@code MapComponent} to a specific location in the {@link Map}.
	 * If you think you should modify this, you're probably doing something wrong.
	 * @param x x coordinate to move to
	 * @param y y coordinate to move to
	 */
	public void moveTo(int x, int y) {
		map.moveComponent(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets an image at a specific dimension (size x size) corresponding to this
	 * {@code MapComponent}s name (located at images/[name].png).
	 * @param  name name of the {@code MapComponent}
	 * @param  size size of the dimension
	 * @return      {@link BufferedImage} the image generated
	 */
	public static BufferedImage getImageByName(String name, int size) {
		try {
			Image img = ImageIO.read(new File("images/" + name + ".png"))
				.getScaledInstance(size, size, Image.SCALE_SMOOTH);
			BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.drawImage(img, 0, 0, null);
			g2d.dispose();

			return bi;
		} catch (IOException e) {
			System.err.printf("Failed to load image in path: %s", "images/" + name + ".png");
			System.exit(1);
			return null;
		}
	}

	/**
	 * Tries getting (or generating) an image for this {@code MapComponent}
	 * @param  size size of image
	 * @return      {@link BufferedImage} the image of this {@code MapComponent}
	 */
	public BufferedImage getImage(int size) {
		if (img == null)
			img = getImageByName(name, size);
		return img;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public Map getMap() { return map; }
	public Direction getDirection() { return direction; }
	public Color getColor() { return color; }
	public String getName() { return name; }
	public int getPrecedence() { return precedence; }

	/**
	 * If you want to stun, call {@link #preventUpdate(int)}, not this.
	 * @return true if  update prevented, false otherwise
	 */
	public boolean preventUpdate() {
		if (preventUpdate > 0) {
			preventUpdate--;
			return true;
		} else return false;
	}

	public boolean isSolid() { return solid; }
	public boolean isOpaque() { return opaque; }
	public boolean isInvincible() { return invincible; }
	public int getDelayInterval() { return delayInterval; }

	/**
	 * Sets whether or not this {@code MapComponent} is solid
	 * @param solid true if solid, false otherwise
	 */
	public void setSolid(boolean solid) { this.solid = solid; }

	/**
	 * Sets whether or not this {@code MapComponent} is opaque
	 * @param opaque true if opaque, false otherwise
	 */
	public void setOpaque(boolean opaque) { this.opaque = opaque; }

	/**
	 * Sets the {@link Direction} this {@code MapComponent} is facing
	 * @param dir the {@link Direction} to face
	 */
	public void setDirection(Direction dir) { direction = dir; }

	/**
	 * Sets the {@link Color} for the {@link finalescape.gui.MinimapPanel} to
	 * display.
	 * @param color the {@link Color} to display
	 * @see finalescape.gui.MinimapPanel
	 */
	public void setColor(Color color) { this.color = color; }

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setMap(Map map) { this.map = map; }

	/**
	 * Sets precedence of this {@code MapComponent}. For example, a component
	 * with a precedence of 5 will tick before one with a precedence of 3.
	 * @param precedence the precedence value to set
	 */
	public void setPrecedence(int precedence) { this.precedence = precedence; }

	/**
	 * Sets the delay interval (an interval of 20 means that it ticks once every
	 * 20 game ticks (like {@link Teacher}s)). There is a tick every 50 ms.
	 * @param interval the length of the interval
	 */
	public void setDelayInterval(int interval) { delayInterval = interval; }

	public void setInvincible(boolean invin) { invincible = invin; }

	/**
	 * Prevents updating this {@code MapComponent} a specific number of times
	 * (used for 'stunning' components).
	 * @param numMoves how many moves to prevent updating
	 */
	public void preventUpdate(int numMoves) {
		if (preventUpdate < numMoves)
			preventUpdate = numMoves;
	}

	/**
	 * Called whenever this {@code MapComponent} should update. Override (like
	 * the 'act' method in GridWorld, but infinitely better).
	 */
	public void tick() {}

	/**
	 * A sweet pseudo-instanceof except dynamic.
	 * @param  obj1 one of the {@link Object}s to use
	 * @param  obj2 the other
	 * @return      true if is an instanceof, false otherwise
	 */
	public final boolean instof(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return false;
		return obj2.getClass().isAssignableFrom(obj1.getClass());
	}

	@Override
	public String toString() {
		if (map != null)
			return String.format("[MapComponent %s in %d % d]", name, x, y);
		else return String.format("[MapComponent %s out of map]", name);
	}

	/**
	 * Destroys this {@code MapComponent} (by default removes from map), but can
	 * be overridden.
	 * @see finalescape.mapcomponent.Failure#destroy
	 */
	public void destroy() {
		if (!invincible)
			map.removeComponent(this);
	}

	/**
	 * Distance between this {@code MapComponent} to another
	 * @param  that the other {@code MapComponent}
	 * @return      the 'as the crow flies' distance between them
	 */
	public double distance(MapComponent that) {
		return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
	}

	/**
	 * Finds the {@link Direction} to face to get to the target {@code MapComponent}.
	 * @param  componentTo the target {@code MapComponent}
	 * @return             the {@link Direction} to face
	 * @see finalescape.map.Map#solveMazeDirection
	 */
	public Direction solveMazeDirection(MapComponent componentTo) {
		return map.solveMazeDirection(this, componentTo);
	}
}