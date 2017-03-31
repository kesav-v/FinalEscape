import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

	private int inventoryCapacity;
	private Item selectedItem;

	public Inventory(int capacity) {
		super(capacity);
		this.inventoryCapacity = capacity;
		selectedItem = null;
	}

	public int capacity() { return inventoryCapacity; }

	@Override
	public boolean add(Item item) {
		if (size() >= inventoryCapacity)
			return false;
		return super.add(item);
	}

	@Override
	public Item get(int index) {
		if (index >= size())
			return null;
		return super.get(index);
	}

	public boolean selectItem(Item item) {
		if (contains(item))
			selectedItem = item;
		else return false;
		return true;
	}

	public Item getSelectedItem() { return selectedItem; }
}