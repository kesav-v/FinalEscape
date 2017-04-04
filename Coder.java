import java.awt.Color;

public class Coder extends Character {

	public Coder(Map map, int x, int y) {
		super(map, x, y, "Coder", 5);
		setColor(Color.BLUE);
		getInventory().add(new Boomerang());
	}

	@Override
	public boolean canMoveHere(int x, int y) {
		MapComponent componentThere = getMap().get(x, y);
		Inventory inventory = getInventory();

		if (componentThere == null)
			return true;
		else if (componentThere instanceof ItemComponent) {
			if (inventory.isFull() && componentThere.getName().equals("Laptop"))
				inventory.set(inventory.getSelectedItemIndex(),
					((ItemComponent)componentThere).getItem());
			return true;
		} else return !componentThere.isSolid();
	}

	@Override
	public void destroy() {
		getMap().loseGame();
	}
}