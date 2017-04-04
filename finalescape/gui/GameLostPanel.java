package finalescape.gui;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class GameLostPanel extends JPanel {

	private FinalEscapeFrame mainPanel;
	private JButton retryButton;

	public GameLostPanel(FinalEscapeFrame mainPanel) {
		this.mainPanel = mainPanel;
		setLayout(null);
	}

	private void addRetryButton(int stringHeight) {
		retryButton = new JButton("Try Again?");
		retryButton.setFont(new Font("Arial", Font.BOLD, 60));
		retryButton.setActionCommand("retry");
		retryButton.addActionListener(mainPanel);
		retryButton.setSize(getWidth() / 4, getWidth() / 16);
		retryButton.setLocation(getWidth() / 2 - getWidth() / 8,
			getHeight() / 2 + stringHeight);
		add(retryButton);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int stringHeight = drawGameOverMessage(g);
		if (retryButton == null)
			addRetryButton(stringHeight);
	}

	private int drawGameOverMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.setColor(Color.RED);
		String text = "Game over!";
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2,
			getHeight() / 2 - g.getFontMetrics().getAscent() + height / 2);
		return height;
	}
}