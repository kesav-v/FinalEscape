public class Frisbee extends ProjectileItem {

	public Frisbee() {
		super("Frisbee");
		setProjectile(new FrisbeeProjectile(this));
	}
}