public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y, "images/wall.png", Direction.NORTH);
		setSolid(true);
		setOpaque(true);
	}
}