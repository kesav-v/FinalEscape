package finalescape.map;

import finalescape.gui.MapGui;
import finalescape.util.Levels;
import finalescape.util.Direction;
import finalescape.util.Location;
import finalescape.util.MazeGenerator;
import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.Character;
import finalescape.mapcomponent.Coder;
import finalescape.mapcomponent.Wall;
import finalescape.mapcomponent.Desk;
import finalescape.mapcomponent.ItemComponent;
import finalescape.item.Item;
import finalescape.item.Laptop;
import finalescape.item.Frisbee;

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
	private int size;
	private int gameTicks;
	private MapComponent destinationComponent;

	public int minisize;
	public double removedeadendprobability;
	public int removewallradius;
	public double visibilityradius;

	private int levelOn;

	public Map() {
		this(1);
	}

	public Map(int levelOn) {
		this.levelOn = levelOn;
		minisize = 13;
		removedeadendprobability = 0.9;
		removewallradius = 25;
		visibilityradius = 4.2;
		Levels.loadLevel(this, levelOn);
	}

	public void initMap() {
		size = 4 * minisize - 1;
		gameTicks = 0;
		int randX = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		int randY = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		boolean[][] maze = MazeGenerator.generateMaze(size, size, randX, randY);
		MazeGenerator.removeDeadEnds(maze, removedeadendprobability);
		initOccupantArray(maze);
		mainCharacter = new Coder(this, randX - 1, randY - 1);
		removeWalls(removewallradius);
		placeDesk();
	}

	private void initOccupantArray(boolean[][] walls) {
		occupantArray = new MapComponent[size][size];
		for (int i = 0; i < occupantArray.length; i++)
			for (int a = 0; a < occupantArray[i].length; a++)
				if (!walls[i][a]) // wall
					occupantArray[i][a] = new Wall(this, i, a);
	}

	public MapComponent get(int x, int y) {
		return occupantArray[x][y];
	}

	private void removeWalls(int radius) {
		int middle = size / 2;
		for (int i = middle - radius / 2; i <= middle + radius / 2; i++)
			for (int j = middle - radius / 2; j <= middle + radius / 2; j++)
				if (occupantArray[i][j] != null
					&& occupantArray[i][j] instanceof Wall)
					removeComponent(i, j);
	}

	private void placeDesk() {
		int randValue = 2 * (int)(Math.random() * (size / 2 - 3)) + 1;
		Desk desk = null;
		switch ((int)(Math.random() * 4)) {
			case 0:
				desk = new Desk(this, 0, randValue);
				break;
			case 1:
				desk = new Desk(this, size - 1, randValue);
				break;
			case 2:
				desk = new Desk(this, randValue, 0);
				break;
			case 3:
				desk = new Desk(this, randValue, size - 1);
				break;
		}
		addComponent(desk, true);
		destinationComponent = desk;
	}

	public synchronized void randomlySpawnComponent(MapComponent component) {
		int x, y;
		do {
			x = (int)(Math.random() * occupantArray.length);
			y = (int)(Math.random() * occupantArray[x].length);
		} while (!validSpawn(x, y));
		addComponent(component, x, y);
	}

	private boolean validSpawn(int x, int y) {
		if (occupantArray[x][y] != null)
			return false;

		int centerX = getCenterX();
		int centerY = getCenterY();

		if (x - 1 == centerX)
			if (y - 1 == centerY || y + 1 == centerY || y == centerY)
				return false;
			else return true;
		else if (x + 1 == centerX)
			if (y - 1 == centerY || y + 1 == centerY || y == centerY)
				return false;
			else return true;
		else if (x == centerX)
			if (y - 1 == centerY || y + 1 == centerY || y == centerY)
				return false;
			return true;
	}

	public synchronized void addComponent(MapComponent occupant, int x, int y, boolean force) {
		if (!removeComponent(x, y, force))
			return;
		occupantArray[x][y] = occupant;
		occupant.setX(x);
		occupant.setY(y);
		occupant.setMap(this);
	}

	public synchronized void addComponent(MapComponent occupant, int x, int y) {
		addComponent(occupant, x, y, false);
	}

	public synchronized void addComponent(MapComponent occupant, boolean force) {
		addComponent(occupant, occupant.getX(), occupant.getY(), force);
	}

	public synchronized void addComponent(MapComponent occupant) {
		addComponent(occupant, false);
	}

	public synchronized boolean removeComponent(int x, int y, boolean force) {
		if (force);
		else if (x == 0 || y == 0 || x == size - 1 || y == size - 1)
			return false;
		if (occupantArray[x][y] == null)
			return true;
		occupantArray[x][y].setMap(null);
		occupantArray[x][y] = null;
		return true;
	}

	public synchronized boolean removeComponent(int x, int y) {
		return removeComponent(x, y, false);
	}

	public synchronized boolean removeComponent(MapComponent component) {
		if (component.getMap() != null)
			return removeComponent(component.getX(), component.getY());
		return true;
	}

	public synchronized void moveComponent(int fromx, int fromy, int tox, int toy) {
		if (removeComponent(tox, toy))
			occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
	}

	public synchronized void moveMainCharacter(int dx, int dy) {
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

	public MapComponent findLaptop() {
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row)
				if (component == null);
				else if (component.getName().equals("Laptop"))
					return component;
				else if (component instanceof Character)
					if (((Character)component).getInventory()
						.getMostPrecedentedItem() instanceof Laptop)
					return component;
		return null;
	}

	public ArrayList<Character> getMapCharacters() {
		ArrayList<Character> characters = new ArrayList<Character>();
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row)
				if (component != null && component instanceof Character)
					characters.add((Character)component);
		return characters;
	}

	public ArrayList<MapComponent> getMapComponents() {
		ArrayList<MapComponent> components = new ArrayList<MapComponent>();
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row) {
				if (component != null && component.getDelayInterval() == 0)
					System.out.println(component);
				if (component != null
					&& gameTicks % component.getDelayInterval() == 0)
					components.add(component);
			}

		components.sort(componentTickComparator);

		return components;
	}

	public synchronized void gameTick() {
		ArrayList<MapComponent> componentsToTick = getMapComponents();
		for (MapComponent component : componentsToTick)
			if (component != null && component.getMap() != null)
				component.tick();
		gameTicks++;
		if (gameTicks % 1000 == 0)
			randomlySpawnComponent(new ItemComponent(new Frisbee()));
		if (findLaptop() == null)
			randomlySpawnComponent(new ItemComponent(new Laptop()));
		if (componentsToTick.size() > 0)
			updateGui();
	}

	private synchronized void updateGui() {
		gui.updateMap();
	}

	public void loseGame() {
		gui.loseGame();
	}

	public void winGame() {
		gui.winGame();
	}

	public void setGui(MapGui gui) {
		this.gui = gui;
	}

	public Direction solveMazeDirection(MapComponent from, MapComponent to) {
		if (to == null)
			return Direction.IN_PLACE;

		boolean[][] locsVisited = new boolean[size][size];
		for (boolean[] row : locsVisited)
			for (int i = 0; i < row.length; i++)
				row[i] = false;
		ArrayList<Location> queue = new ArrayList<Location>(size * size);
		queue.add(new Location(from.getX(), from.getY()));
		locsVisited[from.getX()][from.getY()] = true;
		Location finalLoc = new Location(to.getX(), to.getY());
		boolean init = true;
		int[] ranDs = MazeGenerator.getRanDs(4);
		Location nextLoc;

		while (queue.size() > 0) {
			Location loc = queue.remove(0);
			if (loc.equals(finalLoc))
				return loc.getDirection();
			for (int dir : ranDs)
				switch (dir) {
					case 0:
						nextLoc = new Location(loc.getX() - 1, loc.getY());
						if (mazeSolveValid(nextLoc, locsVisited)) {
							queue.add(nextLoc);
							if (init)
								nextLoc.setDirection(Direction.WEST);
							else nextLoc.setDirection(loc.getDirection());
						}
						break;
					case 1:
						nextLoc = new Location(loc.getX(), loc.getY() - 1);
						if (mazeSolveValid(nextLoc, locsVisited)) {
							queue.add(nextLoc);
							if (init)
								nextLoc.setDirection(Direction.NORTH);
							else nextLoc.setDirection(loc.getDirection());
						}
						break;
					case 2:
						nextLoc = new Location(loc.getX() + 1, loc.getY());
						if (mazeSolveValid(nextLoc, locsVisited)) {
							queue.add(nextLoc);
							if (init)
								nextLoc.setDirection(Direction.EAST);
							else nextLoc.setDirection(loc.getDirection());
						}
						break;
					case 3:
						nextLoc = new Location(loc.getX(), loc.getY() + 1);
						if (mazeSolveValid(nextLoc, locsVisited)) {
							queue.add(nextLoc);
							if (init)
								nextLoc.setDirection(Direction.SOUTH);
							else nextLoc.setDirection(loc.getDirection());
						}
						break;
				}
				if (init)
					init = false;
		}
		return Direction.IN_PLACE;
	}

	private boolean mazeSolveValid(Location loc, boolean[][] locsVisited) {
		if (loc.getX() < 0 || loc.getX() > size - 1 || loc.getY() < 0 ||
			loc.getY() > size - 1)
			return false;
		if (locsVisited[loc.getX()][loc.getY()])
			return false;
		locsVisited[loc.getX()][loc.getY()] = true;
		if (get(loc.getX(), loc.getY()) instanceof Wall)
			return false;
		return true;
	}

	public int getCenterX() { return mainCharacter.getX(); }
	public int getCenterY() { return mainCharacter.getY(); }
	public Character getMainCharacter() { return mainCharacter; }
	public MapComponent getDestinationComponent() { return destinationComponent; }
	public int size() { return size; }
	public double getVisibilityRadius() { return visibilityradius; }
	public int getLevelOn() { return levelOn; }
	public int getGameTicks() { return gameTicks; }

	public void setLevelOn(int level) { levelOn = level; }
}
