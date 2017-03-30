/**
 * This program makes a 2d maze using DFS
 * EDIT (5/4/15): Added 3d functionality
 * @author  Ofek Gila
 * @since 	April 22nd, 2015
 */
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
public class MazeGenerator	{
	private static final int blackrgb = new Color(0, 0, 0).getRGB();
	private static final int whitergb = new Color(255, 255, 255).getRGB();

	public static void main(String... pumpkins) {
		// printMaze(generateMaze(31, 31, 5, 2, 2, 1)); // Sample of generating 3d maze
		// printMaze(generateMaze(101, 101, 10, 10));	// Sample of generating 2d maze
		// saveImage(generateMaze(51, 51, 10, 10), "Maze.png");	// Sample of saving 2d maze as png image
	}
	/**
	 * Prints a two dimensional maze from an array
	 * of booleans where the walls lay in false.
	 * @param maze the two dimensional maze
	 */
	public static void printMaze(boolean[][] maze)	{
		for (int i = 0; i < maze.length; i++)	{
			for (int a = 0; a < maze[i].length; a++)
				System.out.print(maze[i][a] ? "*":"|");
			System.out.println();
		}
	}
	/**
	 * Prints a three dimensional maze from an array
	 * of booleans where the walls lay in false.
	 * @param maze the three dimensional maze
	 */
	public static void printMaze(boolean[][][] maze)	{
		for (int z = 0; z < maze[0][0].length; z++)	{
			for (int x = 0; x < maze.length; x++)	{
				for (int y = 0; y < maze[x].length; y++)
					System.out.print(maze[x][y][z] ? "*":"|");
				System.out.println();
			}
			new Scanner(System.in).nextLine();
			System.out.println();
		}
	}
	/**
	 * Generates a two dimensional maze given a width and height
	 * @param  width  the width of the maze
	 * @param  height the height of the maze
	 * @return        a two dimensional maze
	 */
	public static boolean[][] generateMaze(int width, int height)	{
		return generateMaze(width, height, 0, 0);
	}
	/**
	 * Generates a two dimensional maze given a width, height,
	 * startX and startY positions for starting the maze. If the
	 * startX or startY are even, then they will be walled.
	 * @param  width  the width of the maze
	 * @param  height the height of the maze
	 * @param  startX the starting x location for the maze
	 * @param  startY the starting y location for the maze
	 * @return        a two dimensional maze
	 */
	public static boolean[][] generateMaze(int width, int height, int startX, int startY)	{
		boolean[][] maze = new boolean[width+2][height+2];
		maze[startX][startY] = true;
		generateMaze(maze, startX, startY);
		boolean[][] realmaze = new boolean[width][height];
		for (int i = 0; i < realmaze.length; i++)
			for (int a = 0; a < realmaze[i].length; a++)
				realmaze[i][a] = maze[i+1][a+1];
		return realmaze;
	}
	/**
	 * The recursive method used to generate a 2d maze. When
	 * making a new array, set the array elements to false by
	 * default, except the location at (x, y).
	 * @param maze the two dimensional maze to run on
	 * @param x    the current x position
	 * @param y    the current y position
	 */
	public static void generateMaze(boolean[][] maze, int x, int y)	{
		int[] tempRandDs = getRanDs(4);
		for (int i = 0; i < tempRandDs.length; i++)	{
			switch (tempRandDs[i])	{
				case 0:	{
					if (y - 2 <= 0) continue;
					if (maze[x][y-2] == false)	{
						maze[x][y-1] = true;
						maze[x][y-2] = true;
						mustGenMaze(maze, x, y-2);
					}
					break;
				}	
				case 1:	{
					if (y + 2 >= maze[x].length - 1) continue;
					if (maze[x][y+2] == false)	{
						maze[x][y+1] = true;
						maze[x][y+2] = true;
						mustGenMaze(maze, x, y+2);
					}
					break;
				}
				case 2:	{
					if (x + 2 >= maze.length - 1) continue;
					if (maze[x+2][y] == false)	{
						maze[x+1][y] = true;
						maze[x+2][y] = true;
						mustGenMaze(maze, x+2, y);
					}
					break;
				}
				case 3:	{
					if (x - 2 <= 0) continue;
					if (maze[x-2][y] == false)	{
						maze[x-1][y] = true;
						maze[x-2][y] = true;
						mustGenMaze(maze, x-2, y);
					}
					break;
				}
			}
		}
	}
	/**
	 * Generates a three dimensional maze given a width, height
	 * and depth.
	 * @param  width  the width of the maze
	 * @param  height the height of the maze
	 * @param  depth  the depth of the maze
	 * @return        a three dimensional maze
	 */
	public static boolean[][][] generateMaze(int width, int height, int depth)	{
		return generateMaze(width, height, depth, 0, 0, 0);
	}
	/**
	 * Generates a three dimensional maze given a width, height,
	 * depth, startX, startY, and startZ positions for starting
	 * the maze. If the startX, startY, or startZ are even, then
	 * they will be walled.
	 * @param  width  the width of the maze
	 * @param  height the height of the maze
	 * @param  depth  the depth of the maze
	 * @param  startX the starting x location for the maze
	 * @param  startY the starting y location for the maze
	 * @param  startZ the starting z location for the maze
	 * @return        a three dimensional maze 
	 */
	public static boolean[][][] generateMaze(int width, int height, int depth, int startX, int startY, int startZ)	{
		boolean[][][] maze = new boolean[width+2][height+2][depth+2];
		maze[startX][startY][startZ] = true;
		generateMaze(maze, startX, startY, startZ);
		boolean[][][] realmaze = new boolean[width][height][depth];
		for (int x = 0; x < realmaze.length; x++)
			for (int y = 0; y < realmaze[x].length; y++)
				for (int z = 0; z < realmaze[x][y].length; z++)
					realmaze[x][y][z] = maze[x+1][y+1][z+1];
		return realmaze;
	}
	/**
	 * The recursive method used to generate a 3d maze. When
	 * making a new array, set the array elements to false by
	 * default, except the location at (x, y, z).
	 * @param maze the two dimensional maze to run on
	 * @param x    the current x position
	 * @param y    the current y position
	 * @param z    the current z position
	 */
	public static void generateMaze(boolean[][][] maze, int x, int y, int z)	{
		int[] tempRandDs = getRanDs(6);
		for (int i = 0; i < tempRandDs.length; i++)	{
			switch (tempRandDs[i])	{
				case 0:	{
					if (y - 2 <= 0) continue;
					if (maze[x][y-2][z] == false)	{
						maze[x][y-1][z] = true;
						maze[x][y-2][z] = true;
						mustGenMaze(maze, x, y-2, z);
					}
					break;
				}	
				case 1:	{
					if (y + 2 >= maze[x].length - 1) continue;
					if (maze[x][y+2][z] == false)	{
						maze[x][y+1][z] = true;
						maze[x][y+2][z] = true;						
						mustGenMaze(maze, x, y+2, z);
					}
					break;
				}
				case 2:	{
					if (x + 2 >= maze.length - 1) continue;
					if (maze[x+2][y][z] == false)	{
						maze[x+1][y][z] = true;
						maze[x+2][y][z] = true;
						mustGenMaze(maze, x+2, y, z);
					}
					break;
				}
				case 3:	{
					if (x - 2 <= 0) continue;
					if (maze[x-2][y][z] == false)	{
						maze[x-1][y][z] = true;
						maze[x-2][y][z] = true;
						mustGenMaze(maze, x-2, y, z);
					}
					break;
				}
				case 4:	{
					if (z - 2 <= 0)	continue;
					if (maze[x][y][z-2] == false)	{
						maze[x][y][z-1] = true;
						maze[x][y][z-2] = true;
						mustGenMaze(maze, x, y, z-2);
					}
					break;
				}
				case 5:	{
					if (z + 2 >= maze[x][y].length - 1)	continue;
					if (maze[x][y][z+2] == false)	{
						maze[x][y][z+1] = true;
						maze[x][y][z+2] = true;
						mustGenMaze(maze, x, y, z+2);
					}
					break;
				}
			}
		}
	}
	private static void mustGenMaze(boolean[][][] maze, int x, int y, int z)	{	// removes need for many try catches
		try	{
			generateMaze(maze, x, y, z);
		}	catch (StackOverflowError e)	{
			mustGenMaze(maze, x, y, z);
		}
	}
	private static void mustGenMaze(boolean[][] maze, int x, int y)	{
		try	{
			generateMaze(maze, x, y);
		}	catch (StackOverflowError e)	{
			mustGenMaze(maze, x, y);
		}
	}
	/**
	 * Returns an array of ints from 0 to num-1 of length num,
	 * in a random order
	 * @param  num the length of the array
	 * @return     an array of ints
	 */
	public static int[] getRanDs(int num)	{
		int[] temp = new int[num];
		for (int i = 0; i < temp.length; i++)
			temp[i] = -1;
		int ran = 0;
		for (int i = 0; i < temp.length; i++)	{
			do {
				ran = (int)(Math.random() * temp.length);
			}	while (temp[ran] != -1);
			temp[ran] = i;
		}
		return temp;
	}
	/**
	 * Saves an two dimensional maze as a png image
	 * @param maze     the two dimensional array
	 * @param imageloc the location to save it to (include ".png")
	 */
	public static void saveImage(boolean[][] maze, String imageloc)	{
		int rgb;
		BufferedImage image = new BufferedImage(maze.length, maze[0].length, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < maze.length; x++)
    		for(int y = 0; y < maze[x].length; y++)
    			image.setRGB(x, y, maze[x][y] ? whitergb:blackrgb);
    	File outputfile = new File(imageloc);
    	try {
			ImageIO.write(image, "png", outputfile);
    	}
    	catch (IOException e)	{
    		System.out.println("Error Writing to File");
    		System.exit(5);
    	}
    }
}