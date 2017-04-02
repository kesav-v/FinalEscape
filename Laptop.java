public class Laptop extends Item {
	public Laptop() {
		super("Laptop");
	}

	@Override
	public boolean onUse(Character character) { return false; }
}