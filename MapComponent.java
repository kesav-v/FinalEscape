import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import java.awt.Color;
public abstract class MapComponent {

	private int x, y;
	private Map map;
	private boolean solid, opaque;
	private Image img;
	private Direction direction;
	private Color color;

	public MapComponent(Map map, int x, int y, String imgPath) {
		this.map = map;
		this.x = x;
		this.y = y;
		map.addComponent(this);
		direction = Direction.NORTH;
		solid = opaque = false;
		color = Color.GRAY;
		try {
			img = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			System.err.printf("Failed to load image in path: %s", imgPath);
			System.exit(1);
		}
	}

	// DON'T USE EXCEPT FOR TEMP COMPONENTS!!!
	public MapComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public final void moveTo(int x, int y) {
		moveTo(x, y, true);
	}

	public final void moveTo(int x, int y, boolean updateGui) {
		map.moveComponent(this.x, this.y, x, y, updateGui);
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public Map getMap() { return map; }
	public Image getImage() { return img; }
	public Direction getDirection() { return direction; }
	public Color getColor() { return color; }

	public boolean isSolid() { return solid; }
	public boolean isOpaque() { return opaque; }

	public void setSolid(boolean solid) { this.solid = solid; }
	public void setOpaque(boolean opaque) { this.opaque = opaque; }
	public void setDirection(Direction dir) { direction = dir; }
	public void setColor(Color color) { this.color = color; }
}