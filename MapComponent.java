import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public abstract class MapComponent {

	private int x, y;
	private Map map;
	private boolean solid, opaque;
	private Image img;

	public MapComponent(Map map, int x, int y, String imgPath) {
		this.map = map;
		this.x = x;
		this.y = y;
		map.addComponent(this);
		solid = opaque = false;
		try {
			img = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			System.err.println("Rip");
			System.exit(1);
		}
	}

	public final void moveTo(int x, int y) {
		map.moveComponent(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public Map getMap() { return map; }
	public Image getImage() { return img; }

	public boolean isSolid() { return solid; }
	public boolean isOpaque() { return opaque; }

	public void setSolid(boolean solid) { this.solid = solid; }
	public void setOpaque(boolean opaque) { this.opaque = opaque; }
}