import java.awt.Color;
import java.awt.Image;

public abstract class Item {

	private String name;
	private Color color;
	private Image image;
	private int precedence;
	private int maxUses;
	private int uses;

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

	public void setImage(Image img) { image = img; }

	public Image getImage() {
		if (image == null)
			image = MapComponent.getImageByName(name);
		return image;
	}

	public boolean placeIfPossible(Character character) {
		if (canPlace(character)) {
			place(character);
			return true;
		}
		return false;
	}

	public boolean canPlace(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		return canPlaceOn(character.getMap().get(spawnx, spawny));
	}

	public boolean canPlaceOn(MapComponent component) {
		return component == null;
	}

	public void place(Character character) {
		Direction dir = character.getDirection();
		int spawnx = character.getX() + dir.dX;
		int spawny = character.getY() + dir.dY;
		place(character.getMap(), spawnx, spawny, character.getDirection());
	}

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