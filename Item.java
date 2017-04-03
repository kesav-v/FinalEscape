import java.awt.Color;
import java.awt.Image;

public abstract class Item {

	private String name;
	private Color color;
	private Image image;

	public Item(String name, Color color) {
		this.name = name;
		this.color = color;
		image = null;
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

	public String getName() { return name; }
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }

	/**
	 * What happens when used
	 * @param  character {@link Character} with item equipped
	 * @return           true if to delete, false otherwise
	 */
	public abstract boolean onUse(Character character);
}