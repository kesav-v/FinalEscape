import java.awt.Color;

public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y, "Wall");
		setSolid(true);
		setOpaque(true);
		setColor(Color.BLACK);
	}
}