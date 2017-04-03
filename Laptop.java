import java.awt.Color;

public class Laptop extends Item {
	public Laptop() {
		super("Laptop");
		setColor(new Color(212, 175, 55)); // gold
	}

	@Override
	public boolean onUse(Character character) { return false; }
}