import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MapGui extends JFrame implements KeyListener, MouseListener {

	/**
	 * The width of the screen, in pixels.
	 */
	public static final int SCREEN_WIDTH =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	/**
	 * The height of the screen, in pixels.
	 */
	public static final int SCREEN_HEIGHT =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private final int GAME_TICK_DELAY_MILLISECONDS = 1000;

	private Map map;
	private MapPanel mapPanel;
	private MinimapPanel minimapPanel;
	private InventoryPanel inventoryPanel;
	private Timer gameTickTimer;

	public MapGui(Map map, int width, int height) {
		this.map = map;
		map.setGui(this);
		setLayout(null);
		setSize(width, height);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT - 20);
		centerOnScreen();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addMapPanel();
		addMinimapPanel();
		addInventoryPanel();
		startGameClock();

		addKeyListener(this);
		addMouseListener(this);
	}

	private void addMapPanel() {
		mapPanel = new MapPanel(map);
		mapPanel.setLocation(getWidth() / 2 - mapPanel.getWidth() / 2,
			getHeight() / 2 - mapPanel.getHeight() / 2);
		add(mapPanel);
	}

	private void addMinimapPanel() {
		int marginWidth = getWidth() / 2 - mapPanel.getWidth() / 2;
		minimapPanel = new MinimapPanel(map, marginWidth);
		int padding = marginWidth / 2 - minimapPanel.getWidth() / 2;
		minimapPanel.setLocation(padding, padding);
		add(minimapPanel);
	}

	private void addInventoryPanel() {
		inventoryPanel = new InventoryPanel(map.getMainCharacter().getInventory());
		int marginHeight = getHeight() / 2 - mapPanel.getHeight() / 2;
		int maxHeight = (int)(marginHeight * 0.8);

		int width = (int)(getWidth() * 0.85);
		width += 5 - width % 5;
		if (width / 5 > maxHeight)
			width = maxHeight * 5;

		inventoryPanel.setSize(width, width / 5);
		inventoryPanel.setLocation(getWidth() / 2 - width / 2,
			getHeight() / 2 + mapPanel.getHeight() / 2 + (marginHeight / 4 - width / 20));
		add(inventoryPanel);
	}

	public void updateMap() {
		ArrayList<Location> visibleLocations = mapPanel.calculateVisibleLocations();
		mapPanel.repaint();
		minimapPanel.updateMemory(visibleLocations);
		minimapPanel.repaint();
		inventoryPanel.repaint();
	}

	/**
	 * Centers this {@code SimpleHtmlRenderer} on the screen.
	 */
	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}

	public void startGameClock() {
		gameTickTimer = new Timer();
		gameTickTimer.schedule(new GameClock(), GAME_TICK_DELAY_MILLISECONDS);
	}

	public void stopGameClock() {
		gameTickTimer.cancel();
	}

	class GameClock extends TimerTask {
		@Override
		public void run() {
			map.gameTick();
		}
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
	public void mouseClicked(MouseEvent event) {}
}