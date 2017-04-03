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
	private GameWonPanel gameWonPanel;
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
		addGameWonPanel();
	}

	private void addCards() {
		cards.setSize(getWidth(), getHeight());
		cards.setLocation(0, 0);
		add(cards);
	}

	private void addMapGui(int levelOn) {
		mapGui = new MapGui(this, levelOn);
		cards.add(mapGui, "Map");
		mapGui.setFocusable(true);
		mapGui.grabFocus();
	}

	private void addGameLostPanel() {
		gameLostPanel = new GameLostPanel(this);
		cards.add(gameLostPanel, "Game Lost");
	}

	private void addGameWonPanel() {
		gameWonPanel = new GameWonPanel(this);
		cards.add(gameWonPanel, "Game Won");
	}

	private void centerOnScreen() {
		setLocation((SCREEN_WIDTH - getWidth()) / 2,
			(SCREEN_HEIGHT - getHeight()) / 2);
	}

	public void loseGame() {
		((CardLayout)cards.getLayout()).show(cards, "Game Lost");
	}

	public void winGame() {
		((CardLayout)cards.getLayout()).show(cards, "Game Won");
	}
}