import javax.swing.JPanel;
import java.awt.Graphics;

public class MapPanel extends JPanel {

	public static final int BLOCK_SIZE = 50;

	private Map map;
	private int centerX, centerY;

	public MapPanel(Map map) {
		this.map = map;
		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawRect(5, 5, 10, 10);
	}
}