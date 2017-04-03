import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class GameWonPanel extends JPanel {

	private FinalEscape mainPanel;

	public GameWonPanel(FinalEscape mainPanel) {
		this.mainPanel = mainPanel;
	}

	@Override
	public void paintComponent(Graphics g) {
		drawWhiteRectangle(g);
		drawGameOverMessage(g);
	}

	private void drawWhiteRectangle(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawGameOverMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.setColor(Color.GREEN);
		String text = "Victory!";
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2,
			getHeight() / 2 - g.getFontMetrics().getAscent() + height / 2);
	}
}