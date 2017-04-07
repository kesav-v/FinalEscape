package finalescape.mapcomponent;

import finalescape.map.Map;
import finalescape.item.Item;

/**
 * A {@link MapComponent} that displays {@link Item}s. Is not solid, so can be
 * stepped on by {@link Character}s and the {@code Item} picked up.
 *
 * @author Ofek Gila
 * @see Item
 */
public class ItemComponent extends MapComponent {

	private final Item item;

	/**
	 * Initializes an {@code ItemComponent} within a {@link Map}.
	 * @param  map  the {@link Map} to place in
	 * @param  x    x coordinate
	 * @param  y    y coordinate
	 * @param  item {@link Item} to display
	 */
	public ItemComponent(Map map, int x, int y, Item item) {
		super(map, x, y, item.getName());
		this.item = item;
		setColor(item.getColor());
		setSolid(false);
		setOpaque(false);
	}

	/**
	 * Initializes an {@code ItemComponent} outside any {@link Map}.
	 * @param  item the {@link Item} to display
	 */
	public ItemComponent(Item item) {
		super(item.getName());
		this.item = item;
		setColor(item.getColor());
		setSolid(false);
		setOpaque(false);
	}

	/**
	 * Returns the {@link Item} of this component
	 * @return the held {@link Item}
	 */
	public Item getItem() { return item; }
}