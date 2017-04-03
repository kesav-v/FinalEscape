import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class GameWonPanel extends JPanel {

	private FinalEscape mainPanel;
	private JButton continueButton;

	public GameWonPanel(FinalEscape mainPanel) {
		this.mainPanel = mainPanel;
		setLayout(null);
	}

	private void addContinueButton(int stringHeight) {
		continueButton = new JButton("Continue?");
		continueButton.setActionCommand("continue");
		continueButton.addActionListener(mainPanel);
		continueButton.setSize(getWidth() / 4, getWidth() / 16);
		continueButton.setLocation(getWidth() / 2 - getWidth() / 8,
			getHeight() / 2 + stringHeight);
		add(continueButton);
	}

	@Override
	public void paintComponent(Graphics g) {
		drawWhiteRectangle(g);
		int stringHeight = drawGameWonMessage(g);
		if (continueButton == null)
			addContinueButton(stringHeight);
	}

	private void drawWhiteRectangle(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private int drawGameWonMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.setColor(Color.GREEN);
		String text = "Victory!";
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2,
			getHeight() / 2 - g.getFontMetrics().getAscent() + height / 2);
		return height;
	}
}