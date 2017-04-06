package finalescape.gui;

import java.awt.Color;

public class GameWonPanel extends GameOverPanel {
	public GameWonPanel(FinalEscapeFrame mainPanel) {
		super(mainPanel, "Victory!", "Continue?", "continue");
		setMainColor(Color.GREEN);
	}
}