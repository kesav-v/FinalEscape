import java.awt.Color;

public class Teacher extends Character {

	public Teacher() {
		super("Teacher", 1);
		setColor(Color.RED);
		setDirection(Direction.SOUTH);
		// getInventory().add(new TextBook());
	}
}