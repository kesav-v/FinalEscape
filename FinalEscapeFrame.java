import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.CardLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FinalEscapeFrame extends JFrame implements ActionListener {
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

	private int levelOn;

	public FinalEscapeFrame(int width, int height) {
		levelOn = DataManager.loadLevelOn();
		setLayout(null);
		cards = new JPanel(new CardLayout());
		setSize(width, height);
		centerOnScreen();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addCards();
		addMapGui(levelOn);
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
		DataManager.saveLevelOn(levelOn);
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
		showCard("Game Lost");
		removeCard(mapGui);
	}

	public void winGame() {
		showCard("Game Won");
		removeCard(mapGui);
	}

	public void newGame() {
		addMapGui(levelOn);
		showCard("Map");
		mapGui.grabFocus();
	}

	private void showCard(String cardName) {
		((CardLayout)cards.getLayout()).show(cards, cardName);
	}

	private void removeCard(JPanel card) {
		((CardLayout)cards.getLayout()).removeLayoutComponent(card);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case "continue":
				levelOn++;
				newGame();
				break;
			case "retry":
				newGame();
				break;
		}
	}
}