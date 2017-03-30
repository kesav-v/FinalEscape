public class MapComponent {

	private int x, y;
	private Map map;

	public MapComponent(Map map, int x, int y) {
		this.x = x;
		this.y = y;
	}

	public final void moveTo(int x, int y) {
		map.moveComponent(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public Map getMap() { return map; }
}