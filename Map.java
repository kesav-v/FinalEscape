import java.util.ArrayList;

public class Map {

	private MapComponent[][] occupantArray;
	private Character mainCharacter;
	private MapGui gui; // for when things changed, repaint
	private final int size;
	private int gameTicks;

	public Map() {
		this(13);
	}

	public Map(int minisize) {
		size = 4 * minisize - 1;
		int randX = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		int randY = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		boolean[][] maze = MazeGenerator.generateMaze(size, size, randX, randY);
		MazeGenerator.removeDeadEnds(maze, 0.9);
		occupantArray = new MapComponent[size][size];
		for (int i = 0; i < occupantArray.length; i++)
			for (int a = 0; a < occupantArray[i].length; a++)
				if (!maze[i][a]) // wall
					occupantArray[i][a] = new Wall(this, i, a);

		mainCharacter = new Coder(this, randX - 1, randY - 1);
		gameTicks = 0;
		removeWalls(minisize * 2);

		Levels.loadLevel(this, 1);
	}

	public MapComponent get(int x, int y) {
		return occupantArray[x][y];
	}

	public void removeWalls(int radius) {
		int middle = occupantArray.length / 2;
		for (int i = middle - radius / 2; i < middle + radius / 2; i++) {
			for (int j = middle - radius / 2; j < middle + radius / 2; j++) {
				if (occupantArray[i][j] != null && occupantArray[i][j].isSolid()) {
					occupantArray[i][j] = null;
				}
			}
		}
	}

	public void randomlySpawnComponent(MapComponent component) {
		int x, y;
		do {
			x = (int)(Math.random() * occupantArray.length);
			y = (int)(Math.random() * occupantArray[x].length);
		} while (occupantArray[x][y] != null);
		addComponent(component, x, y);
	}

	public void addComponent(MapComponent occupant, int x, int y) {
		occupantArray[x][y] = occupant;
		occupant.setX(x);
		occupant.setY(y);
		occupant.setMap(this);
	}

	public void addComponent(MapComponent occupant) {
		addComponent(occupant, occupant.getX(), occupant.getY());
	}

	public void moveComponent(int fromx, int fromy, int tox, int toy) {
		occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
	}

	public void moveMainCharacter(int dx, int dy) {
		mainCharacter.moveCharacterDelta(dx, dy);
		updateGui();
	}

	public void gameTick() {
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row)
				if (component != null)
					component.tick();
		gameTicks++;
		updateGui();
	}

	private void updateGui() {
		gui.updateMap();
	}

	public void setGui(MapGui gui) {
		this.gui = gui;
	}

	public int getCenterX() { return mainCharacter.getX(); }
	public int getCenterY() { return mainCharacter.getY(); }
	public Character getMainCharacter() { return mainCharacter; }
	public int size() { return size; }
}
