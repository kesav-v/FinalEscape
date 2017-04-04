package finalescape.mapcomponent;

import finalescape.item.Item;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

	private int inventoryCapacity;
	private Item selectedItem;
	private int selectedItemIndex;
	private Item mostPrecedented;

	public Inventory(int capacity) {
		super(capacity);
		this.inventoryCapacity = capacity;
		selectedItem = mostPrecedented = null;
		selectedItemIndex = -1;
	}

	public int capacity() { return inventoryCapacity; }

	@Override
	public boolean add(Item item) {
		if (isFull())
			return false;
		if (size() == 0) {
			selectedItemIndex = 0;
			selectedItem = item;
		}
		if (mostPrecedented == null
			|| item.getPrecedence() > mostPrecedented.getPrecedence())
			mostPrecedented = item;
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
		if (size() == 0) {
			selectedItem = null;
			selectedItemIndex = -1;
		} else {
			selectedItemIndex = (selectedItemIndex + 1) % size();
			selectedItem = get(selectedItemIndex);
		}
	}

	public boolean isFull() {
		return size() >= inventoryCapacity;
	}

	public Item getSelectedItem() { return selectedItem; }
	public int getSelectedItemIndex() { return selectedItemIndex; }

	public void useSelectedItem(Character character) {
		if (selectedItem.onUse(character)) {
			remove(selectedItemIndex);
			switchSelectedItem();
		}
	}

	public Item getMostPrecedentedItem() { return mostPrecedented; }
}