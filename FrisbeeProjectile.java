public class FrisbeeProjectile extends ProjectileComponent {
	public FrisbeeProjectile(Map map, int x, int y, Direction dir, Item item) {
		super(map, x, y, item, dir);
	}

	public FrisbeeProjectile(Item item) {
		super(item);
	}
}