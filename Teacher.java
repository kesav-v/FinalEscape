import java.awt.Color;

public class Teacher extends Character {

	public Teacher(Map map, int x, int y) {
		super(map, x, y, "Teacher", 1);
		setColor(Color.RED);
		// getInventory().add(new TextBook());
	}
}