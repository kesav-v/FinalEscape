public class Person extends MapComponent {

	//private Weapon w;
	private int health;
	private Direction direction;

	public Person(int x, int y) {
		super(x, y);
		//w = new Frisbee();
		health = 100;
		direction = Direction.EAST;
	}
}