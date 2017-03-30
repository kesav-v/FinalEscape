import javax.swing.JFrame;
import java.awt.Toolkit;

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

	public MapGui(Map map, int width, int height) {
		this.map = map;
		map.setGui(this);
		setSize(width, height);
		centerOnScreen();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addMapPanel();
	}

	private void addMapPanel() {
		mapPanel = new MapPanel(map);
		add(mapPanel);
	}

	public void updateMap() {}

	/**
	 * Centers this {@code SimpleHtmlRenderer} on the screen.
	 */
	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}

}