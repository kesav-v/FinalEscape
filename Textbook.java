public class Textbook extends Item {
	public Textbook() {
		super("Textbook");
	}

	@Override
	public boolean onUse(Character character) { return true; }
}