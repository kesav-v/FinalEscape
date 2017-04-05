package finalescape.gui;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public abstract class GameOverPanel extends JPanel {

	private FinalEscapeFrame mainPanel;
	private JButton mainButton;
	private boolean buttonFocus;
	private String mainMessage;
	private String mainButtonText, mainButtonAction;

	private Color mainColor;

	public GameOverPanel(FinalEscapeFrame mainPanel, String mainMessage,
		String mainButtonText, String mainButtonAction) {
		this.mainPanel = mainPanel;
		this.mainMessage = mainMessage;
		this.mainButtonText = mainButtonText;
		this.mainButtonAction = mainButtonAction;
		mainColor = Color.BLACK;
		setLayout(null);
		buttonFocus = false;
	}

	private void addMainButton(int stringHeight) {
		mainButton = new JButton(mainButtonText);
		mainButton.setFont(new Font("Arial", Font.BOLD, 60));
		mainButton.setActionCommand(mainButtonAction);
		mainButton.addActionListener(mainPanel);
		mainButton.setSize(getWidth() / 4, getWidth() / 16);
		mainButton.setLocation(getWidth() / 2 - getWidth() / 8,
			getHeight() / 2 + stringHeight);
		add(mainButton);
	}

	public void setButtonFocus() {
		buttonFocus = true;
	}

	private void focusButton() {
		getRootPane().setDefaultButton(mainButton);
		mainButton.requestFocus();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int stringHeight = drawMainMessage(g);
		if (mainButton == null)
			addMainButton(stringHeight);
		if (buttonFocus) {
			buttonFocus = false;
			focusButton();
		}
	}

	private int drawMainMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.setColor(mainColor);
		String text = mainMessage;
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		g.drawString(text, getWidth() / 2 - width / 2,
			getHeight() / 2 - g.getFontMetrics().getAscent() + height / 2);
		return height;
	}

	public void setMainColor(Color c) { mainColor = c; }
}