import java.awt.Color;

public class Coder extends Character {

	public Coder(Map map, int x, int y) {
		super(map, x, y, "Coder", 5);
		setColor(Color.BLUE);
	}

	@Override
	public boolean moveCharacter(int dx, int dy) {
		int newx = getX() + dx;
		int newy = getY() + dy;
		Inventory inventory = getInventory();

		MapComponent nextComponent = getMap().get(newx, newy);

		if (nextComponent instanceof ItemComponent
			&& inventory.size() < inventory.capacity())
			inventory.add(((ItemComponent)nextComponent).getItem());

		return super.moveCharacter(dx, dy);
	}
}