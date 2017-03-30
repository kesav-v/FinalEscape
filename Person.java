public class Person extends MapComponent {

	//private Weapon w;
	private int health;
	private Direction direction;

	public Person(Map map, int x, int y) {
		super(map, x, y, "Maze.png");
		//w = new Frisbee();
		health = 100;
		direction = Direction.EAST;
	}
}