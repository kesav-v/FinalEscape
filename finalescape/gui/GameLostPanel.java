package finalescape.gui;

import java.awt.Color;

public class GameLostPanel extends GameOverPanel {
	public GameLostPanel(FinalEscapeFrame mainPanel) {
		super(mainPanel, "Game over!", "Try Again?", "retry");
		setMainColor(Color.RED);
	}
}