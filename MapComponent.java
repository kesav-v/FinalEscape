public abstract class MapComponent {

	private int x, y;
	private Map map;

	public MapComponent(Map map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
		map.addComponent(this);
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