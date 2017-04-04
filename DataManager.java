import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class DataManager {

	public static final String LEVEL_ON_PATH = "levelon.txt";

	public static void saveInitialData() {
		BufferedWriter writer = loadWriter(LEVEL_ON_PATH);
		try {
			writer.write("1\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveLevelOn(int levelOn) {
		BufferedWriter writer = loadWriter(LEVEL_ON_PATH);
		try {
			writer.write(levelOn + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int loadLevelOn() {
		Scanner reader = Levels.loadScanner(LEVEL_ON_PATH);
		if (reader == null) {
			saveInitialData();
			return 1;
		}
		return reader.nextInt();
	}

	public static BufferedWriter loadWriter(String path) {
		try {
			return new BufferedWriter(new FileWriter(new File(path)));
		} catch (IOException e) {
			return null;
		}
	}

}