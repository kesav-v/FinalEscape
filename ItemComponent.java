public class ItemComponent extends MapComponent {

	private final Item item;

	public ItemComponent(Map map, int x, int y, Item item) {
		super(map, x, y, item.getName());
		this.item = item;
		setColor(item.getColor());
		item.setImage(getImage());
		setSolid(false);
		setOpaque(false);
	}

	public ItemComponent(Item item) {
		super(item.getName());
		this.item = item;
		setColor(item.getColor());
		item.setImage(getImage());
		setSolid(false);
		setOpaque(false);
	}

	public Item getItem() { return item; }
}