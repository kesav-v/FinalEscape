import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class MinimapPanel extends JPanel {

	public final int BLOCK_SIZE;

	private Map map;
	private Color[][] memory;
	private int middleX, middleY;

	public MinimapPanel(Map map, int maxWidth) {
		this.map = map;
		setSize(maxWidth * 2 / 3, maxWidth * 2 / 3);
		BLOCK_SIZE = getWidth() / map.size();
		setSize(BLOCK_SIZE * map.size(), BLOCK_SIZE * map.size());

		memory = new Color[map.size()][map.size()];
		for (Color[] row : memory)
			for (int i = 0; i < row.length; i++)
				row[i] = new Color(0, 0, 0, 0);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		middleX = (width - BLOCK_SIZE) / 2;
		middleY = (height - BLOCK_SIZE) / 2;
	}

	public void updateMemory(ArrayList<Location> visibleLocations) {
		for (Location loc : visibleLocations) {
			MapComponent comp = map.get(loc.getX(), loc.getY());
			if (comp == null)
				memory[loc.getX()][loc.getY()] = Color.WHITE;
			else memory[loc.getX()][loc.getY()] = comp.getColor();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		drawUnknownMist(g);
		drawBorder(g);
		drawMemory(g);
	}

	private void drawMemory(Graphics g) {
		for (int i = 0; i < memory.length; i++)
			for (int a = 0; a < memory[i].length; a++) {
				g.setColor(memory[i][a]);
				g.fillRect(i * BLOCK_SIZE,
					a * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
			}
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