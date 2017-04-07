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

/**
 * The map of {@link MapComponent}s that handles modifying what's on the map
 *
 * @see MapComponent
 * @author Ofek Gila
 * @author Kesav Viswanadha
 */
public class Map {

	/**
	 * Sorts list of MapComponents for order with which to tick
	 */
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
	public String name;
	public MazeGenerator.GENERATION_STYLE mazestyle;
	public double mazestyleintensity;

	private int levelOn;

	public Map() {
		this(1);
	}

	/**
	 * Loads level from level file using Levels.java.
	 * Beforehand, sets up defaults for parameters that can be overridden by
	 * Levels.java (minisize, name, etc).
	 * @param  levelOn level to load (located in levels/level[levelOn].txt)
	 */
	public Map(int levelOn) {
		this.levelOn = levelOn;
		minisize = 13;
		removedeadendprobability = 0.9;
		removewallradius = 25;
		visibilityradius = 4.2;
		name = "";
		mazestyle = MazeGenerator.GENERATION_STYLE.NORMAL;
		mazestyleintensity = 0.8;
		Levels.loadLevel(this, levelOn);
	}

	/**
	 * Initializes the map (called by Levels.txt when it says "Initialize Map"),
	 * after the settings of the map (such as dimensions are decided).
	 */
	public void initMap() {
		size = 4 * minisize - 1;
		gameTicks = 0;
		int randX = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		int randY = 2 * (int)(Math.random() * (size / 2 - 3)) + 2;
		boolean[][] maze = MazeGenerator.generateMaze(size, size, randX, randY,
			mazestyle, mazestyleintensity);
		MazeGenerator.removeDeadEnds(maze, removedeadendprobability);
		initOccupantArray(maze);
		mainCharacter = new Coder(this, randX - 1, randY - 1);
		removeWalls(removewallradius);
		placeDesk();
	}

	/**
	 * Places walls in the array based off of boolean array (false values)
	 * contain walls.
	 * @param walls boolean array
	 */
	private void initOccupantArray(boolean[][] walls) {
		occupantArray = new MapComponent[size][size];
		for (int i = 0; i < occupantArray.length; i++)
			for (int a = 0; a < occupantArray[i].length; a++)
				if (!walls[i][a]) // wall
					occupantArray[i][a] = new Wall(this, i, a);
	}

	/**
	 * Get {@link MapComponent} at specific coordinates
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   {@link MapComponent} at location
	 */
	public MapComponent get(int x, int y) {
		return occupantArray[x][y];
	}

	/**
	 * Returns true if the location at (x, y) is in bounds and empty, false
	 * otherwise.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   {@link MapComponent} at location
	 */
	public boolean isEmpty(int x, int y) {
		if (x < 0 || x >= size || y < 0 || y >= size)
			return false;
		return get(x, y) == null;
	}

	/**
	 * Remove walls from the middle at a specific radius, used for the 'arena'
	 * in the center. Removes in square shape.
	 * @param radius Radius with which to remove.
	 */
	private void removeWalls(int radius) {
		int middle = size / 2;
		for (int i = middle - radius / 2; i <= middle + radius / 2; i++)
			for (int j = middle - radius / 2; j <= middle + radius / 2; j++)
				if (occupantArray[i][j] != null
					&& occupantArray[i][j] instanceof Wall)
					removeComponent(i, j);
	}

	/**
	 * Places a desk in the map (along edge of board)
	 */
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

	/**
	 * Randomly spawns a specific {@link MapComponent} in the map.
	 * @param component the {@link MapComponent} to spawn
	 */
	public synchronized void randomlySpawnComponent(MapComponent component) {
		int x, y;
		do {
			x = (int)(Math.random() * occupantArray.length);
			y = (int)(Math.random() * occupantArray[x].length);
		} while (!validSpawn(x, y));
		addComponent(component, x, y);
	}

	/**
	 * Returns true if {@link MapComponent} should be able t spawn there,
	 * false otherwise.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if valid, false otherwise
	 */
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

	/**
	 * Tries adding a {@link MapComponent} at a specific location. You should
	 * probably call the one without the boolean unless you know what you're doing.
	 * @param occupant {@link MapComponent} to add
	 * @param x        x coordinate
	 * @param y        y coordinate
	 * @param force    true if can be forced into the edge, false otherwise
	 */
	public synchronized void addComponent(MapComponent occupant, int x, int y, boolean force) {
		if (!removeComponent(x, y, force))
			return;
		occupantArray[x][y] = occupant;
		occupant.setX(x);
		occupant.setY(y);
		occupant.setMap(this);
	}

	/**
	 * Tries adding a {@link MapComponent} at a specific location. This is the
	 * one that should be used.
	 * @param occupant {@link MapComponent} to add
	 * @param x        x coordinate
	 * @param y        y coordinate
	 */
	public synchronized void addComponent(MapComponent occupant, int x, int y) {
		addComponent(occupant, x, y, false);
	}

	/**
	 * Adds a {@link MapComponent} to the map at its current location. Don't use
	 * this one with the boolean unless you know what you're doing
	 * @param occupant {@link MapComponent} to add
	 * @param force    whether or not to force into edge
	 */
	public synchronized void addComponent(MapComponent occupant, boolean force) {
		addComponent(occupant, occupant.getX(), occupant.getY(), force);
	}

	/**
	 * Adds a {@link MapComponent} to the map at its current location.
	 * @param occupant {@link MapComponent} to add
	 */
	public synchronized void addComponent(MapComponent occupant) {
		addComponent(occupant, false);
	}

	/**
	 * Removes a {@link MapComponent} at a specific x, y location. Don't use this
	 * one with the boolean if you don't know what you're doing.
	 * @param  x     x coordinate
	 * @param  y     y coordinate
	 * @param  force whether or not to force remove from the edge
	 * @return       true if removed, false if failed (e.g., tried from edge)
	 */
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

	/**
	 * Removes a {@link MapComponent} at a specific x, y location. Use this one.
	 * @param  x x coordinate
	 * @param  y y coordinate
	 * @return   true if removed, false if failed (e.g., tried from edge)
	 */
	public synchronized boolean removeComponent(int x, int y) {
		return removeComponent(x, y, false);
	}

	/**
	 * Removes a {@link MapComponent} from map. Use this one too.
	 * @param  component the {@link MapComponent} to remove
	 * @return           true if removed, false if failed (e.g., tried from edge)
	 */
	public synchronized boolean removeComponent(MapComponent component) {
		if (component.getMap() != null)
			return removeComponent(component.getX(), component.getY());
		return true;
	}

	/**
	 * Move {@link MapComponent} from an x, y coordinate to another
	 * @param fromx x to move from
	 * @param fromy y to move from
	 * @param tox   x to move to
	 * @param toy   y to move to
	 */
	public synchronized void moveComponent(int fromx, int fromy, int tox, int toy) {
		if (removeComponent(tox, toy))
			occupantArray[tox][toy] = occupantArray[fromx][fromy];
		occupantArray[fromx][fromy] = null;
	}

	/**
	 * Moves the main character by a delta x and delta y (don't call this). This
	 * is used for arrow keys in the GUI, and nothing else.
	 * @param dx delta x to move by
	 * @param dy delta y to move by
	 */
	public synchronized void moveMainCharacter(int dx, int dy) {
		mainCharacter.moveCharacterDelta(dx, dy);
		updateGui();
	}

	/**
	 * Shifts the selected {@link Item} in the main {@link Character}'s {@link finalescape.mapcomponent.Inventory}.
	 * Don't call this (only used in GUI, when shift is clicked).
	 */
	public void shiftSelectedItem() {
		mainCharacter.getInventory().switchSelectedItem();
		updateGui();
	}

	/**
	 * Uses the main {@link Character}'s selected {@link Item}. Only GUI should
	 * call this.
	 */
	public void useSelectedItem() {
		mainCharacter.useSelectedItem();
		updateGui();
	}

	/**
	 * Tries finding the {@link Laptop} in the map
	 * @return {@link MapComponent} the {@link finalescape.mapcomponent.ItemComponent} holding the laptop, or the {@link Character} who has the laptop in their {@link finalescape.mapcomponent.Inventory}.
	 */
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

	/**
	 * Generates a sorted {@code ArrayList} of {@link MapComponent}s to tick,
	 * sorted by component precedence (defined by the components). Note that
	 * does not update components with update prevented, or when their delay
	 * interval isn't up.
	 * @return the ArrayList of MapComponents
	 */
	public ArrayList<MapComponent> getComponentsToTick() {
		ArrayList<MapComponent> components = new ArrayList<MapComponent>();
		for (MapComponent[] row : occupantArray)
			for (MapComponent component : row) {
				if (component != null && component.getDelayInterval() == 0)
					System.out.println(component);
				if (component != null
					&& gameTicks % component.getDelayInterval() == 0
					&& !component.preventUpdate())
					components.add(component);
			}

		components.sort(componentTickComparator);

		return components;
	}

	/**
	 * Calls the {@link finalescape.mapcomponent.MapComponent#tick() tick} method
	 * in all the {@link MapComponent}s to update.
	 */
	public synchronized void gameTick() {
		ArrayList<MapComponent> componentsToTick = getComponentsToTick();
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

	/**
	 * Updates the GUI (probably shouldn't be called, the GUI is updated)
	 * automatically after all {@link Character}s tick. Only call if something
	 * happens between ticks (like the main character doing something).
	 */
	private synchronized void updateGui() {
		gui.updateMap();
	}

	/**
	 * Loses the game
	 */
	public void loseGame() {
		gui.loseGame();
	}

	/**
	 * Wins the game
	 */
	public void winGame() {
		gui.winGame();
	}

	/**
	 * Sets the {@link MapGui} for this {@code Map} (for updating when things
	 * change). Probably shouldn't need to be called.
	 * @param gui [description]
	 */
	public void setGui(MapGui gui) {
		this.gui = gui;
	}

	private int[] ranDs = MazeGenerator.getRanDs(4);

	/**
	 * Finds the shortest path to a given location, and returns the {@link Direction}
	 * to face in order to get to that location.
	 * @param  from {@link MapComponent} trying to get to location
	 * @param  to   {@link MapComponent} trying to get to
	 * @return      {@link Direction} to face to get to second {@code MapComponent}
	 */
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
		int[] ranDs = this.ranDs;
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

	/**
	 * Helper method for {@link #solveMazeDirection}
	 */
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

	/**
	 * Gets the X coordinate of the main {@link Character}
	 * @return main {@link Character} x coordinate
	 */
	public int getCenterX() { return mainCharacter.getX(); }

	/**
	 * Gets the Y coordinate of the main {@link Character}
	 * @return main {@link Character} y coordinate
	 */
	public int getCenterY() { return mainCharacter.getY(); }

	/**
	 * Gets the main {@link Character} of this {@code Map} (the {@link Coder}).
	 * @return the {@code Map}'s main {@link Character}
	 */
	public Character getMainCharacter() { return mainCharacter; }

	/**
	 * Gets the {@link MapComponent} holding the final destination of this {@code Map}
	 * (the {@link Desk}).
	 * @return the final {@link MapComponent} destination
	 */
	public MapComponent getDestinationComponent() { return destinationComponent; }

	/**
	 * Returns the size of this {@code Map}
	 * @return the {@code Map} size
	 */
	public int size() { return size; }

	/**
	 * Gets the visibility radius of the main {@link Character} for this {@code Map}
	 * @return the visibility radius
	 */
	public double getVisibilityRadius() { return visibilityradius; }

	/**
	 * Gets the level this {@code Map} is on
	 * @return the current level
	 */
	public int getLevelOn() { return levelOn; }

	/**
	 * Gets how many ticks have elapsed for this {@code Map}
	 * @return elapsed ticks
	 */
	public int getGameTicks() { return gameTicks; }

	/**
	 * Gets the name of this {@code Map}
	 * @return the name of this {@code Map}
	 */
	public String getName() { return name; }
}
