import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.ArrayList;

public class MapGui extends JFrame {

	/**
	 * The width of the screen, in pixels.
	 */
	private final int SCREEN_WIDTH =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	/**
	 * The height of the screen, in pixels.
	 */
	private final int SCREEN_HEIGHT =
		(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private Map map;
	private MapPanel mapPanel;
	private MinimapPanel minimapPanel;
	private InventoryPanel inventoryPanel;

	public MapGui(Map map, int width, int height) {
		this.map = map;
		map.setGui(this);
		setLayout(null);
		setSize(width, height);
		centerOnScreen();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addMapPanel();
		addMinimapPanel();
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
		add(inventoryPanel);
	}

	public void updateMap() {
		ArrayList<Location> visibleLocations = mapPanel.calculateVisibleLocations();
		mapPanel.repaint();
		minimapPanel.updateMemory(visibleLocations);
		minimapPanel.repaint();
	}

	/**
	 * Centers this {@code SimpleHtmlRenderer} on the screen.
	 */
	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}
}