import javax.swing.JPanel;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class MapPanel extends JPanel {

	public static final int BLOCK_SIZE = 50;
	public static final double VISIBILITY_RADIUS = 4.2;

	private int middleX, middleY;
	private Map map;
	private ArrayList<Location> visibleLocations;
	private boolean gameOver;

	public MapPanel(Map map) {
		this.map = map;
		gameOver = false;
		setSize((int)Math.ceil(VISIBILITY_RADIUS * 2 + 4) * BLOCK_SIZE,
			(int)Math.ceil(VISIBILITY_RADIUS * 2 + 4) * BLOCK_SIZE);
	}

	public void setGameOver(boolean b) {
		gameOver = b;
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
		if (gameOver)
			drawGameOverMessage(g);
		else if (visibleLocations != null)
			drawComponents(g, visibleLocations);
		drawBorder(g);
	}

	private void drawGameOverMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 80));
		Color c = g.getColor();
		g.setColor(Color.RED);
		String text = "Game over!";
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2, middleY + height / 2);
		g.setColor(c);
	}

	private void drawComponents(Graphics g,
		ArrayList<Location> visibleLocations) {
		for (Location loc : visibleLocations)
			drawMapComponent(g, loc);
	}

	private void drawMap(Graphics g) {
		int centerX = map.getCenterX();
		int centerY = map.getCenterY();
		for (int i = 0; i < map.size(); i++)
			for (int a = 0; a < map.size(); a++)
				if (isVisible(centerX, centerY, i, a))
					drawMapComponent(g, i - centerX, a - centerY, i, a);
	}

	private void drawMapComponent(Graphics g, Location loc) {
		int mapX = loc.getX();
		int mapY = loc.getY();
		int x = mapX - map.getCenterX();
		int y = mapY - map.getCenterY();
		drawMapComponent(g, x, y, mapX, mapY);
	}

	private void drawMapComponent(Graphics g, int x, int y, int mapX, int mapY) {
		MapComponent component = map.get(mapX, mapY);
		if (component == null) {
			g.setColor(Color.WHITE);
			g.fillRect(middleX + x * BLOCK_SIZE, middleY + y * BLOCK_SIZE,
				BLOCK_SIZE, BLOCK_SIZE);
		} else {
			Direction dir = component.getDirection();
			Image img = component.getImage();
			if (dir == Direction.NORTH)
				g.drawImage(img, middleX + x * BLOCK_SIZE,
					middleY + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, Color.WHITE, this);
			else {
				AffineTransform at = new AffineTransform();
				at.rotate(Math.toRadians(dir.compassDirection),
					img.getWidth(this) / 2, img.getHeight(this) / 2);
				AffineTransformOp ato = new AffineTransformOp(at,
					AffineTransformOp.TYPE_BILINEAR);
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage(ato.filter((BufferedImage)img, null), middleX + x * BLOCK_SIZE,
					middleY + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, Color.WHITE, this);
			}
		}
	}

	public ArrayList<Location> getVisibleComponents() { return visibleLocations; }

	public ArrayList<Location> calculateVisibleLocations() {
		int centerX = map.getCenterX();
		int centerY = map.getCenterY();

		boolean[][] walls = generateWalls();
		boolean[][] wasHere = new boolean[walls.length][walls[0].length];
		for (boolean[] row : wasHere)
			for (int i = 0; i < row.length; i++)
				row[i] = false;

		traverseMaze(walls, wasHere, (int)VISIBILITY_RADIUS, (int)VISIBILITY_RADIUS);

		visibleLocations = new ArrayList<Location>();
		for (int i = 0; i < wasHere.length; i++)
			for (int a = 0; a < wasHere[i].length; a++)
				if (wasHere[i][a]) {
					int mapx = centerX - (int)VISIBILITY_RADIUS + i;
					int mapy = centerY - (int)VISIBILITY_RADIUS + a;
					Location loc = new Location(mapx, mapy);
					visibleLocations.add(loc);
					addAdjacentLocations(visibleLocations, mapx, mapy);
				}
		addComponent(visibleLocations, centerX, centerY);

		return visibleLocations;
	}

	private void addAdjacentLocations(ArrayList<Location> visibleLocations,
		int x, int y) {
		addComponent(visibleLocations, x - 1, y);
		addComponent(visibleLocations, x + 1, y);
		addComponent(visibleLocations, x, y - 1);
		addComponent(visibleLocations, x, y + 1);
		addComponent(visibleLocations, x - 1, y - 1);
		addComponent(visibleLocations, x + 1, y + 1);
		addComponent(visibleLocations, x + 1, y - 1);
		addComponent(visibleLocations, x - 1, y + 1);
	}

	private void addComponent(ArrayList<Location> visibleLocations, int x,
		int y) {
		if (x < 0 || x >= map.size() || y < 0 || y >= map.size())
			return;
		MapComponent comp = map.get(x, y);
		if (comp != null && comp.isOpaque() && isVisible(x, y)
			&& !visibleLocations.contains(new Location(x, y)))
			visibleLocations.add(new Location(x, y));
	}

	private void traverseMaze(boolean[][] walls, boolean[][] wasHere, int x, int y) {
		wasHere[x][y] = true;

		if (x > 0 && !wasHere[x - 1][y] && !walls[x - 1][y])
			traverseMaze(walls, wasHere, x - 1, y);
		if (y > 0 && !wasHere[x][y - 1] && !walls[x][y - 1])
			traverseMaze(walls, wasHere, x, y - 1);
		if (x < walls.length - 1 && !wasHere[x + 1][y] && !walls[x + 1][y])
			traverseMaze(walls, wasHere, x + 1, y);
		if (y < walls[x].length - 1 && !wasHere[x][y + 1] && !walls[x][y + 1])
			traverseMaze(walls, wasHere, x, y + 1);
	}

	private boolean[][] generateWalls() {
		boolean[][] walls = new boolean[(int)VISIBILITY_RADIUS * 2 + 1]
			[(int)VISIBILITY_RADIUS * 2 + 1];

		int centerX = map.getCenterX();
		int centerY = map.getCenterY();

		for (int i = 0; i < walls.length; i++)
			for (int a = 0; a < walls[i].length; a++) {
				int mapx = centerX - (int)VISIBILITY_RADIUS + i;
				int mapy = centerY - (int)VISIBILITY_RADIUS + a;
				if (mapx < 0 || mapx >= map.size()
					|| mapy < 0 || mapy >= map.size())
					walls[i][a] = true; // wall (out of bounds)
				else if (map.get(mapx, mapy) != null &&
					map.get(mapx, mapy).isOpaque())
					walls[i][a] = true;
				else if (distance(centerX, centerY, mapx, mapy) <= VISIBILITY_RADIUS)
					walls[i][a] = false;
				else walls[i][a] = true;
			}

		return walls;
	}

	private boolean isVisible(int visx, int visy) {
		return isVisible(map.getCenterX(), map.getCenterY(), visx, visy);
	}

	private boolean isVisible(int currx, int curry, int visx, int visy) {
		if (distance(currx, curry, visx, visy) <= VISIBILITY_RADIUS)
			return true;
		return false;
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

	private double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}