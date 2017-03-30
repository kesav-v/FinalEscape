import java.awt.Color;

public class Coder extends Character {

	public Coder(Map map, int x, int y) {
		super(map, x, y, "Coder");
		inventory = new Inventory(5);
		setColor(Color.BLUE);
	}
}