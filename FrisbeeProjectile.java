public class FrisbeeProjectile extends ProjectileComponent {
	public FrisbeeProjectile(Map map, int x, int y, Direction dir) {
		super(map, x, y, "Frisbee", dir);
	}

	public FrisbeeProjectile() {
		super("Frisbee");
	}
}