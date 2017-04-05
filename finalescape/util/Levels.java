package finalescape.util;

import finalescape.map.Map;
import finalescape.util.MazeGenerator;
import finalescape.mapcomponent.MapComponent;
import finalescape.mapcomponent.ItemComponent;
import finalescape.item.Item;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Constructor;

public class Levels {

	public static final String LEVELS_PATH = "levels/";
	private static String[] HEADERS = {
		"Variables",
		"MapComponents",
		"Items",
	};
	private static String[] COMMANDS = {
		"Initialize Map",
	};

	public static void loadLevel(Map map, int levelNum) {
		parseLevel(map, loadScanner(LEVELS_PATH + "level" + levelNum + ".txt"));
	}

	public static void parseLevel(Map map, Scanner level) {
		String headerOn = "";
		while (level.hasNextLine())
			headerOn = parseLine(map, level.nextLine(), headerOn);
	}

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
		switch (headerOn) {
			case "Variables":
				parseVariable(map, line);
				break;
			case "MapComponents":
				map.randomlySpawnComponent(getMapComponent(line));
				break;
			case "Items":
				map.randomlySpawnComponent(getItemComponent(line));
				break;
		}
		return headerOn;
	}

	private static void parseVariable(Map map, String line) {
		String varname = getVarName(line);
		String varValue = getVarValue(line);
		switch (varname) {
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

	public static Scanner loadScanner(String path) {
		try {
			return new Scanner(new File(path));
		} catch (IOException e) {
			return null;
		}
	}
}