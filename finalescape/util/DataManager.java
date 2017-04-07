package finalescape.util;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Saves the level currently on in levelon.txt, and loads the level from it.
 *
 * @author Ofek Gila
 */
public class DataManager {

	/**
	 * The path to the level file
	 */
	public static final String LEVEL_ON_PATH = "levelon.txt";

	/**
	 * Writes initial data to levelon.txt (1).
	 */
	public static void saveInitialData() {
		BufferedWriter writer = loadWriter(LEVEL_ON_PATH);
		try {
			writer.write("1\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current level on
	 * @param levelOn the level on
	 */
	public static void saveLevelOn(int levelOn) {
		BufferedWriter writer = loadWriter(LEVEL_ON_PATH);
		try {
			writer.write(levelOn + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads what level the player is currently on
	 * @return what the current level is
	 */
	public static int loadLevelOn() {
		Scanner reader = Levels.loadScanner(LEVEL_ON_PATH);
		if (reader == null) {
			saveInitialData();
			return 1;
		}
		return reader.nextInt();
	}

	/**
	 * Loads a {@link BufferedWriter} object at a specific oath
	 * @param  path the path to the file to get the writer from
	 * @return      the {@link BufferedWriter} object
	 */
	public static BufferedWriter loadWriter(String path) {
		try {
			return new BufferedWriter(new FileWriter(new File(path)));
		} catch (IOException e) {
			return null;
		}
	}
}