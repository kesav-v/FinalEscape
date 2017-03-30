import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class MapPanel extends JPanel {

	public static final int BLOCK_SIZE = 25;
	public static final int VISIBILITY_RADIUS = 4;

	private int middleX, middleY;
	private Map map;

	public MapPanel(Map map) {
		this.map = map;
		setLayout(null);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		middleX = (width - BLOCK_SIZE) / 2;
		middleY = (height - BLOCK_SIZE) / 2;
	}

	@Override
	public void paintComponent(Graphics g) {
		drawUnknownMist(g);
		drawBorder(g);
		drawMap(g);
	}

	private void drawMap(Graphics g) {
		int centerX = map.getCenterX();
		int centerY = map.getCenterY();
		for (int i = -VISIBILITY_RADIUS; i <= VISIBILITY_RADIUS; i++)
			if (i + centerX >= 0 && i + centerX < map.size())
				for (int a = -VISIBILITY_RADIUS; a <= VISIBILITY_RADIUS; a++)
					if (a + centerY >= 0 && a + centerY < map.size())
						drawMapComponent(g, i, a, i + centerX, a + centerY);
	}

	private void drawMapComponent(Graphics g, int x, int y, int mapX, int mapY) {
		MapComponent component = map.getComponent(mapX, mapY);
		if (component == null) {
			g.setColor(Color.WHITE);
			g.fillRect(middleX + x * BLOCK_SIZE, middleY + y * BLOCK_SIZE,
				BLOCK_SIZE, BLOCK_SIZE);
		} else g.drawImage(component.getImage(), middleX + x * BLOCK_SIZE,
			middleY + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, this);
		// Block size is 25 by default (arbitrary)
		// literally everything seems to work but 25/26 o_O
		// but the way this runs, is that how we want it
		// what do you mean
		// like that looks like a bunch of tiny mazes put together
		// yeah, so we get a better image (more fitting)
	}

	private void drawUnknownMist(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), BLOCK_SIZE);
		g.fillRect(0, getHeight() - BLOCK_SIZE, getWidth(), BLOCK_SIZE);
		g.fillRect(0, 0, BLOCK_SIZE, getHeight());
		g.fillRect(getWidth() - BLOCK_SIZE, 0, BLOCK_SIZE, getHeight());
	}
}