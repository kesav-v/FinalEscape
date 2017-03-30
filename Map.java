import java.util.ArrayList;

public class Map {
	private MapComponent[][] occupantArray;
	private MapGui gui; // for when things changed, repaint

	public Map() {
		this(13);
	}

	public Map(int minisize) {
		int size = 4 * minisize - 1;
		int randX = 2 * (int)(Math.random() * (size / 2 - 1));
		int randY = 2 * (int)(Math.random() * (size / 2 - 1));
		boolean[][] maze = MazeGenerator.generateMaze(size, size, randX, randY);
		occupantArray = new MapComponent[size][size];
		for (int i = 0; i < occupantArray.length; i++)
			for (int a = 0; a < occupantArray[i].length; a++)
				if (!maze[i][a]) // wall
					occupantArray[i][a] = new Wall(this, i, a);
	}

	public static void main(String[] args) {
	}

	public void addComponent(MapComponent occupant) {
		occupantArray[occupant.getX()][occupant.getY()] = occupant;
	}

	public void moveComponent(int fromx, int fromy, int tox, int toy) {
		occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
		updateGui();
	}

	private void updateGui() {
		gui.updateMap();
	}

	public void setGui(MapGui gui) {
		this.gui = gui;
	}
}
