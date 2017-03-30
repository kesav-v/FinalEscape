public class Wall extends MapComponent {
	public Wall(Map map, int x, int y) {
		super(map, x, y, "Maze.png");
		setSolid(true);
		setOpaque(true);
	}
}