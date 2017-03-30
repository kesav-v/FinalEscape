public abstract class Weapon {

	private int damage;
	private int cost;

	public Weapon(int d, int c) {
		damage = d;
		cost = c;
	}

	public int getDamage() {return damage;}
	public int getCost() {return cost;}

	public abstract void fire();
}