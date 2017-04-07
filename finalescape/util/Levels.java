package finalescape.util;

import finalescape.map.Map;
import finalescape.util.MazeGenerator;
import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.Teacher;
import finalescape.mapcomponent.ItemComponent;
import finalescape.item.Item;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Constructor;

/**
 * Utility class for loading levels from level txt files (e.g. levels/level1.txt)
 * into {@link Map} using reflection.
 * Example format for such files is:
 *
 * <pre>
	Variables

	minisize 5
	removedeadendprobability 1
	removewallradius 10
	visibilityradius 5.3
	name Starting Fresh

	Initialize Map

	MapComponents

	Teacher 5

	Items

	Laptop 1
	Frisbee 5
	</pre>

	@author Ofek Gila
 */
public class Levels {

	public static final String LEVELS_PATH = "levels/";
	private static String[] HEADERS = {
		"Variables",
		"MapComponents",
		"BossTeachers",
		"Items",
	};
	private static String[] COMMANDS = {
		"Initialize Map",
	};

	/**
	 * Loads a level given a level number. Level file is found at
	 * levels/level[level number].txt
	 * @param map      the {@link Map} to load the level into
	 * @param levelNum the number of the level
	 */
	public static void loadLevel(Map map, int levelNum) {
		parseLevel(map, loadScanner(LEVELS_PATH + "level" + levelNum + ".txt"));
	}

	/**
	 * Parse a level given a {@link Scanner} representation of that level file.
	 * @param map   the {@link Map} to load the level into
	 * @param level a {@link Scanner} representation of the level file
	 */
	public static void parseLevel(Map map, Scanner level) {
		String headerOn = "";
		while (level.hasNextLine())
			headerOn = parseLine(map, level.nextLine(), headerOn);
	}

	/**
	 * Parse a single line in the level file as {@code String}.
	 * @param  map      the {@link Map} to parse the line into
	 * @param  line     the {@code String} line
	 * @param  headerOn the current header (e.g. MapComponents, Items, etc.)
	 * @return          the current header
	 */
	private static String parseLine(Map map, String line, String headerOn) {
		if (line.length() == 0)
			return headerOn;
		if (isHeader(line))
			return line;
		else if (isCommand(line)) {
			switch(line) {
				case "Initialize Map":
					map.initMap();
					break;
			}
			return headerOn;
		}
		String name = getVarName(line);
		String val = getVarValue(line);
		switch (headerOn) {
			case "Variables":
				parseVariable(map, name, val);
				break;
			case "MapComponents":
				for (int i = 0; i < Integer.parseInt(val); i++)
					map.randomlySpawnComponent(getMapComponent(name));
				break;
			case "BossTeachers":
				for (int i = 0; i < Integer.parseInt(val); i++)
					map.randomlySpawnComponent(getBossTeacher(name));
				break;
			case "Items":
				for (int i = 0; i < Integer.parseInt(val); i++)
					map.randomlySpawnComponent(getItemComponent(name));
				break;
		}
		return headerOn;
	}

	/**
	 * Parse a variable, given when the {@code Variables} header is active.
	 * @param map      the {@link Map} to parse the variable into
	 * @param varName  the name of the variable
	 * @param varValue the value of the variable
	 */
	private static void parseVariable(Map map, String varName, String varValue) {
		switch (varName) {
			case "minisize":
				map.minisize = Integer.parseInt(varValue);
				break;
			case "removedeadendprobability":
				map.removedeadendprobability = Float.parseFloat(varValue);
				break;
			case "removewallradius":
				map.removewallradius = Integer.parseInt(varValue);
				break;
			case "visibilityradius":
				map.visibilityradius = Float.parseFloat(varValue);
				break;
			case "name":
				map.name = varValue;
				break;
			case "mazestyle":
				map.mazestyle = MazeGenerator.GENERATION_STYLE.valueOf(varValue);
				break;
			case "mazestyleintensity":
				map.mazestyleintensity = Double.parseDouble(varValue);
				break;
		}
	}

	private static String getVarName(String line) {
		return line.substring(0, line.indexOf(' '));
	}

	private static String getVarValue(String line) {
		return line.substring(line.indexOf(' ') + 1);
	}

	private static MapComponent getMapComponent(String className) {
		try {
			Class<?> clazz = Class.forName("finalescape.mapcomponent." + className);
			Constructor<?> constructor = clazz.getConstructor();
			return (MapComponent)constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static MapComponent getBossTeacher(String className) {
		try {
			Class<?> clazz = Class.forName("finalescape.mapcomponent." + className);
			Constructor<?> constructor = clazz.getConstructor();
			Teacher teacher = (Teacher)constructor.newInstance();
			teacher.becomeBoss();
			return teacher;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ItemComponent getItemComponent(String className) {
		try {
			Class<?> clazz = Class.forName("finalescape.item." + className);
			Constructor<?> constructor = clazz.getConstructor();
			return new ItemComponent((Item)constructor.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean isHeader(String line) {
		for (String header : HEADERS)
			if (line.equals(header))
				return true;
		return false;
	}

	private static boolean isCommand(String line) {
		for (String command : COMMANDS)
			if (line.equals(command))
				return true;
		return false;
	}

	/**
	 * Loads a {@link Scanner} for a file given a file path.
	 * @param  path the path to the file
	 * @return      a {@link Scanner} representation of that file.
	 */
	public static Scanner loadScanner(String path) {
		try {
			return new Scanner(new File(path));
		} catch (IOException e) {
			return null;
		}
	}
}