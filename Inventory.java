import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

	private int inventoryCapacity;
	private Item selectedItem;
	private int selectedItemIndex;

	public Inventory(int capacity) {
		super(capacity);
		this.inventoryCapacity = capacity;
		selectedItem = null;
		selectedItemIndex = -1;
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
		if (contains(item)) {
			selectedItem = item;
			selectedItemIndex = indexOf(item);
		} else return false;
		return true;
	}

	public void switchSelectedItem() {
		if (size() == 0)
			return;
		selectedItemIndex = (selectedItemIndex + 1) % size();
		selectedItem = get(selectedItemIndex);
	}

	public Item getSelectedItem() { return selectedItem; }
	public int getSelectedItemIndex() { return selectedItemIndex; }
}