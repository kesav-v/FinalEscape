import java.util.ArrayList;

public class Map {

	private MapComponent[][] occupantArray;
	private MapGui gui; // for when things changed, repaint
	private int centerX, centerY;
	private final int size;

	public Map() {
		this(13);
	}

	public Map(int minisize) {
		size = 4 * minisize - 1;
		int randX = 2 * (int)(Math.random() * (size / 2 - 1));
		int randY = 2 * (int)(Math.random() * (size / 2 - 1));
		boolean[][] maze = MazeGenerator.generateMaze(size, size, randX, randY);
		MazeGenerator.removeDeadEnds(maze, 0.8);
		occupantArray = new MapComponent[size][size];
		for (int i = 0; i < occupantArray.length; i++)
			for (int a = 0; a < occupantArray[i].length; a++)
				if (!maze[i][a]) // wall
					occupantArray[i][a] = new Wall(this, i, a);

		centerX = centerY = 4;
	}

	public static void main(String[] args) {
	}

	public MapComponent getComponent(int x, int y) {
		return occupantArray[x][y];
	}

	public void addComponent(MapComponent occupant) {
		occupantArray[occupant.getX()][occupant.getY()] = occupant;
	}

	public void moveComponent(int fromx, int fromy, int tox, int toy) {
		occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
		updateGui();
	}

	public void movePerson(int dx, int dy) {
		centerX += dx;
		centerY += dy;
		updateGui();
	}

	private void updateGui() {
		gui.updateMap();
	}

	public void setGui(MapGui gui) {
		this.gui = gui;
	}

	public int getCenterX() { return centerX; }
	public int getCenterY() { return centerY; }
	public int size() { return size; }
}
