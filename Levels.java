import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Constructor;

public class Levels {

	public static final String LEVELS_PATH = "levels/";
	private static String[] HEADERS = {
		"MapComponents",
		"Items",
	};

	public static void loadLevel(Map map, int levelNum) {
		parseLevel(map, loadFile(LEVELS_PATH + "level" + levelNum + ".txt"));
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
		switch (headerOn) {
			case "MapComponents":
				map.randomlySpawnComponent(getMapComponent(line));
				break;
			case "Items":
				map.randomlySpawnComponent(getItemComponent(line));
				break;
		}
		return headerOn;
	}

	private static MapComponent getMapComponent(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor();
			return (MapComponent)constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ItemComponent getItemComponent(String className) {
		try {
			Class<?> clazz = Class.forName(className);
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

	public static Scanner loadFile(String path) {
		try {
			return new Scanner(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}