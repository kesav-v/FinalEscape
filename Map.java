import java.util.ArrayList;
import java.util.Comparator;

public class Map {

	private final Comparator<MapComponent> componentTickComparator = new Comparator<MapComponent>() {
		@Override
		public int compare(MapComponent obj1, MapComponent obj2) {
			return obj2.getPrecedence() - obj1.getPrecedence();
		}
	};

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
		placeDesk();
	}

	public MapComponent get(int x, int y) {
		return occupantArray[x][y];
	}

	private void removeWalls(int radius) {
		int middle = occupantArray.length / 2;
		for (int i = middle - radius / 2; i < middle + radius / 2; i++)
			for (int j = middle - radius / 2; j < middle + radius / 2; j++)
				if (occupantArray[i][j] != null && occupantArray[i][j] instanceof Wall)
					removeComponent(i, j);
	}

	private void placeDesk() {
		int randValue = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		switch ((int)(Math.random() * 4)) {
			case 0:
				addComponent(new Desk(this, 0, randValue), true);
				break;
			case 1:
				addComponent(new Desk(this, size - 1, randValue), true);
				break;
			case 2:
				addComponent(new Desk(this, randValue, 0), true);
				break;
			case 3:
				addComponent(new Desk(this, randValue, size - 1), true);
				break;
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

	public void addComponent(MapComponent occupant, int x, int y, boolean force) {
		if (!removeComponent(x, y, force))
			return;
		occupantArray[x][y] = occupant;
		occupant.setX(x);
		occupant.setY(y);
		occupant.setMap(this);
	}

	public void addComponent(MapComponent occupant, int x, int y) {
		addComponent(occupant, x, y, false);
	}

	public void addComponent(MapComponent occupant, boolean force) {
		addComponent(occupant, occupant.getX(), occupant.getY(), force);
	}

	public void addComponent(MapComponent occupant) {
		addComponent(occupant, false);
	}

	public boolean removeComponent(int x, int y, boolean force) {
		if (force);
		else if (x == 0 || y == 0 || x == size - 1 || y == size - 1)
			return false;
		if (occupantArray[x][y] == null)
			return true;
		occupantArray[x][y].setMap(null);
		occupantArray[x][y] = null;
		return true;
	}

	public boolean removeComponent(int x, int y) {
		return removeComponent(x, y, false);
	}

	public boolean removeComponent(MapComponent component) {
		if (component.getMap() != null)
			return removeComponent(component.getX(), component.getY());
		return true;
	}

	public void moveComponent(int fromx, int fromy, int tox, int toy) {
		if (removeComponent(tox, toy))
			occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
	}

	public void moveMainCharacter(int dx, int dy) {
		mainCharacter.moveCharacterDelta(dx, dy);
		updateGui();
	}

	public void shiftSelectedItem() {
		mainCharacter.getInventory().switchSelectedItem();
		updateGui();
	}

	public void useSelectedItem() {
		mainCharacter.useSelectedItem();
		updateGui();
	}

	public ArrayList<MapComponent> getMapComponents() {
		ArrayList<MapComponent> components = new ArrayList<MapComponent>();
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row)
				if (component != null)
					components.add(component);

		components.sort(componentTickComparator);

		return components;
	}

	public void gameTick() {
		for (MapComponent component : getMapComponents())
			if (component != null && component.getMap() != null)
				component.tick();
		gameTicks++;
		updateGui();
	}

	private void updateGui() {
		gui.updateMap();
	}

	public void loseGame() {
		gui.stopGameClock();
		gui.setGameOver(true);
	}

	public void setGui(MapGui gui) {
		this.gui = gui;
	}

	public int getCenterX() { return mainCharacter.getX(); }
	public int getCenterY() { return mainCharacter.getY(); }
	public Character getMainCharacter() { return mainCharacter; }
	public int size() { return size; }
}
