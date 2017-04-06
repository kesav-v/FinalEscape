package finalescape.mapcomponent;

/**
 * An {@link ArrayList} of {@link Item}s that a {@link Character} has. The
 * 'capacity' of this list is the maximum size, you cannot add more items than
 * the capacity allows, and it must be initialized with a capacity.
 *
 * It is this way because it makes removing items and reordering them easier,
 * and variable inventory sizes might eventually be implemented (an item that
 * can increase your inventory size or something).
 *
 * @author Ofek Gila
 * @see Character
 * @see Item
 */

import finalescape.item.Item;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

	private int inventoryCapacity;
	private Item selectedItem;
	private int selectedItemIndex;
	private Item mostPrecedented;

	/**
	 * Initializes an {@code Inventory} with a specific capacity
	 * @param  capacity the maximum storage space of this {@code Inventory}
	 */
	public Inventory(int capacity) {
		super(capacity);
		this.inventoryCapacity = capacity;
		selectedItem = mostPrecedented = null;
		selectedItemIndex = -1;
	}

	/**
	 * Returns the capacity of this {@code Inventory}.
	 * @return the inventory capacity
	 */
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
	public Item set(int index, Item item) {
		if (mostPrecedented == null
			|| item.getPrecedence() > mostPrecedented.getPrecedence())
			mostPrecedented = item;
		return super.set(index, item);
	}

	@Override
	public Item get(int index) {
		if (index >= size())
			return null;
		return super.get(index);
	}

	/**
	 * Selects a specific {@link Item} from this {@code Inventory}, returning
	 * false if the {@code Item} cannot be found.
	 * @param  item the {@link Item} to select
	 * @return      true if successfully selected, false otherwise (cannot be found)
	 */
	public boolean selectItem(Item item) {
		if (contains(item)) {
			selectedItem = item;
			selectedItemIndex = indexOf(item);
		} else return false;
		return true;
	}

	/**
	 * Switches the selected {@link Item} of this {@code Inventory}. Shifts by one
	 * until reaches the end of the capacity, and then loops back.
	 */
	public void switchSelectedItem() {
		if (size() == 0) {
			selectedItem = null;
			selectedItemIndex = -1;
		} else {
			selectedItemIndex = (selectedItemIndex + 1) % size();
			selectedItem = get(selectedItemIndex);
		}
	}

	/**
	 * Returns true if the {@code Inventory} is full, false otherwise
	 * @return true if full, false otherwise
	 */
	public boolean isFull() {
		return size() >= inventoryCapacity;
	}

	/**
	 * Gets the {@link Item} currently selected by this {@code Inventory}.
	 * @return the selected {@link Item}
	 */
	public Item getSelectedItem() { return selectedItem; }

	/**
	 * Returns the index of the selected {@link Item}.
	 * @return the index
	 */
	public int getSelectedItemIndex() { return selectedItemIndex; }

	/**
	 * Uses the selected {@link Item} by the specific {@link Character}.
	 * @param character the {@link Character} that uses the item
	 */
	public void useSelectedItem(Character character) {
		if (selectedItem.onUse(character)) {
			remove(selectedItemIndex);
			switchSelectedItem();
		}
	}

	/**
	 * Gets the {@link Item} with the highest precedence in this {@code Inventory}.
	 * E.g. The {@link finalescape.item.Laptop} has the highest precedence.
	 * @return the {@link Item} with the highest precedence
	 */
	public Item getMostPrecedentedItem() { return mostPrecedented; }
}