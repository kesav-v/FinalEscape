import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.CardLayout;

public class FinalEscape extends JFrame {
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

	private MapGui mapGui;
	private GameLostPanel gameLostPanel;
	private JPanel cards;

	public FinalEscape(int width, int height) {
		setLayout(null);
		cards = new JPanel(new CardLayout());
		setSize(width, height);
		centerOnScreen();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addCards();
		addMapGui(1);
		addGameLostPanel();
		mapGui.gameLost();
		// ((CardLayout)cards.getLayout()).show(cards, "Map");
	}

	private void addCards() {
		cards.setSize(getWidth(), getHeight());
		cards.setLocation(0, 0);
		add(cards);
	}

	private void addMapGui(int levelOn) {
		mapGui = new MapGui(this, getWidth(), getHeight(), levelOn);
		cards.add(mapGui, "Map");
		mapGui.setFocusable(true);
		mapGui.grabFocus();
	}

	private void addGameLostPanel() {
		gameLostPanel = new GameLostPanel(mapGui);
		cards.add(gameLostPanel, "Game Lost");
	}

	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}

	public void gameLost() {
		((CardLayout)cards.getLayout()).show(cards, "Game Lost");
	}
}