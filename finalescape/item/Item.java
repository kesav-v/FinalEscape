package finalescape.item;

/**
 * Items to be held in a {@link Character}'s {@link Inventory}. Can be displayed
 * in the {@link Map} using a {@link ItemComponent}. Can be picked up by
 * {@link Character}s.
 *
 * @see Inventory
 * @see ItemComponent
 * @see Character
 */

import finalescape.map.Map;
import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.Character;
import finalescape.mapcomponent.ItemComponent;
import finalescape.util.Direction;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Item {

	private String name;
	private Color color;
	private BufferedImage image;
	private int precedence;
	private int maxUses;
	private int uses;

	/**
	 * Creates an {@code Item} with a specific name and item. The name should
	 * correspond to an image file at images/[name].png
	 * @param  name  the name of this {@code Item}
	 * @param  color the {@link Color} of this {@code Item} to display in the
	 * minimap
	 */
	public Item(String name, Color color) {
		this.name = name;
		this.color = color;
		image = null;
		precedence = 0;
		uses = 0;
		maxUses = 1;
	}

	public Item(String name) {
		this(name, new Color(100, 216, 105));
	}

	/**
	 * Gets or creates an image of this item with specific dimensions (size x size)
	 * @param  size size of image to create
	 * @return      the {@link BufferedImage} generated
	 */
	public BufferedImage getImage(int size) {
		if (image == null)
			image = MapComponent.getImageByName(name, size);
		return image;
	}

	/**
	 * Tries placing this item if possible
	 * @param  character the {@link Character} that is placing the item
	 * @return           true of placed, false otherwise
	 */
	public boolean placeIfPossible(Character character) {
		if (canPlace(character)) {
			place(character);
			return true;
		} else return false;
	}

	/**
	 * Returns true if the {@link Character} can place this {@code Item}, false
	 * otherwise.
	 * @param  character the {@link Character} to place the item
	 * @return           true of can place, false otherwise
	 */
	public boolean canPlace(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		return canPlaceOn(character.getMap().get(spawnx, spawny));
	}

	/**
	 * Returns true if can place on a specific {@link MapComponent}, false otherwise
	 * @param  component {@link MapComponent} to place on
	 * @return           true if can place, false otherwise
	 */
	public boolean canPlaceOn(MapComponent component) {
		return component == null;
	}

	/**
	 * Places this {@code Item}.
	 * @param character the {@link Character} that places the item.
	 */
	public void place(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		place(character.getMap(), spawnx, spawny, character.getDirection());
	}

	/**
	 * Places this {@code Item} in a specific {@link Map}, with specific
	 * coordinates, and with a specific {@link Direction}
	 * @param map the {@link Map} to place in
	 * @param x   x coordinate
	 * @param y   y coordinate
	 * @param dir the {@link Direction} to place in
	 */
	public void place(Map map, int x, int y, Direction dir) {
		new ItemComponent(map, x, y, this);
	}

	public String getName() { return name; }
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	public int getPrecedence() { return precedence; }
	public void setPrecedence(int precedence) { this.precedence = precedence; }

	public float getOpacity() {
		return 1f - uses * 1f / maxUses;
	}

	/**
	 * What happens when used
	 * @param  character {@link Character} with item equipped
	 * @return           true if to delete, false otherwise
	 */
	public abstract boolean onUse(Character character);

	public int maxUses() { return maxUses; }
	public int getUses() { return uses; }
	public void setUses(int uses) { this.uses = uses; }
	public void setMaxUses(int maxUses) { this.maxUses = maxUses; }
	public void incrementUses() { uses++; }
}