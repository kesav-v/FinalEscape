import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MapGui extends JPanel implements KeyListener, MouseListener {

	private final int GAME_TICK_DELAY_MILLISECONDS = 50;
	private final int KEY_PRESSED_DELAY_MILLISECONDS = 100;

	private FinalEscape mainFrame;
	private Map map;
	private MapPanel mapPanel;
	private MinimapPanel minimapPanel;
	private InventoryPanel inventoryPanel;

	private Timer gameTickTimer;
	private Timer keyPressedTimer;
	private KeyClock keyClock;

	private int levelOn;

	private int lastPressedOnKey;

	public MapGui(FinalEscape mainFrame, int levelOn) {
		this.mainFrame = mainFrame;
		this.levelOn = levelOn;
		gameTickTimer = new Timer();
		keyClock = null;
		map = new Map(levelOn);
		map.setGui(this);
		setLayout(null);
		setSize(mainFrame.getWidth(), mainFrame.getHeight());
		addMapPanel();
		addMinimapPanel();
		addInventoryPanel();
		startGameClock();

		addKeyListener(this);
		addMouseListener(this);
	}

	public MapGui(FinalEscape mainFrame) {
		this(mainFrame, 1);
	}

	public void loseGame() {
		stopGameClock();
		mainFrame.loseGame();
	}

	public void winGame() {
		stopGameClock();
		mainFrame.winGame();
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
		inventoryPanel.initializeItemPanels();
		add(inventoryPanel);
	}

	public void updateMap() {
		ArrayList<Location> visibleLocations = mapPanel.calculateVisibleLocations();
		mapPanel.repaint();
		minimapPanel.updateMemory(visibleLocations);
		minimapPanel.repaint();
		inventoryPanel.repaint();
	}

	public void startGameClock() {
		gameTickTimer.scheduleAtFixedRate(new GameClock(), 0,
			GAME_TICK_DELAY_MILLISECONDS);
	}

	public Map getMap() { return this.map; }

	public void stopGameClock() {
		gameTickTimer.cancel();
	}

	private void startKeyPressedTimer() {
		keyPressedTimer = new Timer();
		keyClock = new KeyClock();
		keyPressedTimer.scheduleAtFixedRate(keyClock, 0,
			KEY_PRESSED_DELAY_MILLISECONDS);
	}

	private void stopKeyPressedTimer() {
		keyClock.cancel();
		keyClock = null;
	}

	class GameClock extends TimerTask {
		@Override
		public void run() {
			map.gameTick();
		}
	}

	class KeyClock extends TimerTask {
		@Override
		public void run() {
			parseKeyCode(lastPressedOnKey);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (keyClock != null)
			stopKeyPressedTimer();
		lastPressedOnKey = -1;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (lastPressedOnKey == event.getKeyCode()) {
			if (keyClock == null)
				startKeyPressedTimer();
			return;
		}
		if (keyClock != null)
			stopKeyPressedTimer();
		lastPressedOnKey = event.getKeyCode();
		parseKeyCode(lastPressedOnKey);
	}

	private void parseKeyCode(int keyCode) {
		switch (keyCode) {
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
			case KeyEvent.VK_SHIFT:
				map.shiftSelectedItem();
				break;
			case KeyEvent.VK_SPACE:
				map.useSelectedItem();
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