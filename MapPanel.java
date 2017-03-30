import javax.swing.JPanel;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class MapPanel extends JPanel implements KeyListener, MouseListener {

	public static final int BLOCK_SIZE = 25;
	public static final double VISIBILITY_RADIUS = 4.2;

	private int middleX, middleY;
	private Map map;

	public MapPanel(Map map) {
		this.map = map;
		setLayout(null);
		addKeyListener(this);
		addMouseListener(this);
		setSize((int)Math.ceil(VISIBILITY_RADIUS * 2 + 4) * BLOCK_SIZE,
			(int)Math.ceil(VISIBILITY_RADIUS * 2 + 4) * BLOCK_SIZE);
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
		if (hasFocus()) {
			drawMap(g);
			// ArrayList<MapComponent> visibleComponents = getVisibleComponents();
			// drawComponents(g, visibleComponents);
		}
		drawBorder(g);
	}

	private void drawComponents(Graphics g,
		ArrayList<MapComponent> visibleComponents) {
		for (MapComponent component : visibleComponents)
			drawMapComponent(g, component);
	}

	private void drawMap(Graphics g) {
		int centerX = map.getCenterX();
		int centerY = map.getCenterY();
		for (int i = 0; i < map.size(); i++)
			for (int a = 0; a < map.size(); a++)
				if (isVisible(centerX, centerY, i, a))
					drawMapComponent(g, i - centerX, a - centerY, i, a);
	}

	private void drawMapComponent(Graphics g, MapComponent component) {
		int mapX = component.getX();
		int mapY = component.getY();
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
			if (dir.compassDirection == 0)
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

	public ArrayList<MapComponent> getVisibleComponents() {
		int centerX = map.getCenterX();
		int centerY = map.getCenterY();

		ArrayList<MapComponent> visibleComponents = new ArrayList<MapComponent>();

		// hallway left

		return visibleComponents;
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

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				map.moveMainCharacter(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				map.moveMainCharacter(1, 0);
				break;
			case KeyEvent.VK_UP:
				map.moveMainCharacter(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				map.moveMainCharacter(0, 1);
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (!hasFocus()) {
			grabFocus();
			repaint();
		}
	}
}