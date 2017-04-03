import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class GameLostPanel extends JPanel {

	private MapGui mapGui;

	public GameLostPanel(MapGui mapGui) {
		this.mapGui = mapGui;
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
		g.setColor(Color.RED);
		String text = "Game over!";
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2,
			getHeight() / 2 - g.getFontMetrics().getAscent() + height / 2);
	}
}