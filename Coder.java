public class Coder extends Character {

	private Inventory inventory;

	public Coder(Map map, int x, int y) {
		super(map, x, y, "images/coder.png");
		inventory = new Inventory();
	}
}