public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y, "images/wall.png");
		setSolid(true);
		setOpaque(true);
	}
}