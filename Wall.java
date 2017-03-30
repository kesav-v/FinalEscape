public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y);
		setSolid(true);
		setOpaque(true);
	}
}