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

	public void setImage(Image img) { image = img; }

	public Image getImage() {
		if (image == null)
			image = MapComponent.getImageByName(name);
		return image;
	}

	public String getName() { return name; }
	public Color getColor() { return color; }
}