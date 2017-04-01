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
		System.out.println("here " + line);
		switch (headerOn) {
			case "MapComponents":
				map.randomlySpawnComponent((MapComponent)getObject(line));
				break;
		}
		return headerOn;
	}

	private static Object getObject(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor();
			return constructor.newInstance();
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