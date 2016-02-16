import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class CreateGrids {
	static Cell[][] grid;
	/*
	 * Method is used to initialize the grid as being blocked or not blocked, 
	 * it is called from main method
	 * */
	public static void initialize_grid(int x, int y) {

		if (x >= 0 && x < grid.length && y >= 0 && y < grid.length
				&& grid[x][y].visited != true) {

			grid[x][y].visited = true;
			// all the possible blocks around it
			if (x >= 0 && x < grid.length && y - 1 >= 0 && y - 1 < grid.length
					&& grid[x][y - 1].visited != true)
				initialize_grid(x, y - 1);
			if (x >= 0 && x < grid.length && y + 1 >= 0 && y + 1 < grid.length
					&& grid[x][y + 1].visited != true)
				initialize_grid(x, y + 1);
			if (x + 1 >= 0 && x + 1 < grid.length && y + 1 >= 0
					&& y + 1 < grid.length
					&& grid[x + 1][y + 1].visited != true)
				initialize_grid(x + 1, y + 1);
			if (x + 1 >= 0 && x + 1 < grid.length && y - 1 >= 0
					&& y - 1 < grid.length
					&& grid[x + 1][y - 1].visited != true)
				initialize_grid(x + 1, y - 1);
			if (x + 1 >= 0 && x + 1 < grid.length && y >= 0 && y < grid.length
					&& grid[x + 1][y].visited != true)
				initialize_grid(x + 1, y);
			if (x - 1 >= 0 && x - 1 < grid.length && y - 1 >= 0
					&& y - 1 < grid.length
					&& grid[x - 1][y - 1].visited != true)
				initialize_grid(x - 1, y - 1);
			if (x - 1 >= 0 && x - 1 < grid.length && y + 1 >= 0
					&& y + 1 < grid.length
					&& grid[x - 1][y + 1].visited != true)
				initialize_grid(x - 1, y + 1);
			if (x - 1 >= 0 && x - 1 < grid.length && y >= 0 && y < grid.length
					&& grid[x - 1][y].visited != true)
				initialize_grid(x - 1, y);

			if (((int) Math.floor(Math.random() * 101)) <= 30) {
				grid[x][y].blocked = true;
				grid[x][y].value = '1';
			}
		}

		return;
	}

	public static void main(String[] args) {
		// its the list of all the 50 maps that we have
		Maze[] list = new Maze[50];
		// creating those cells and initializing their grids
		for (int i = 0; i < list.length; i++) {
			
			list[i] = new Maze(101); // THE SIZE OF 2D ARRAY

			/*
			 * RANDOMLY GET THE START AND END POINT OF THE MAP
			 */
			int x = (int) Math.floor(Math.random() * list[i].grid.length);
			int y = (int) Math.floor(Math.random() * list[i].grid[0].length);
			list[i].start_x = x;
			list[i].start_y = y;
			// changing the value of the start point in the grid
			list[i].grid[x][y].value = 'A';
			list[i].grid[x][y].visited = true;
			x = (int) Math.floor(Math.random() * list[i].grid.length);
			y = (int) Math.floor(Math.random() * list[i].grid[0].length);
			list[i].end_x = x;
			list[i].end_y = y;
			// changing the value of the end point in the grid
			list[i].grid[x][y].value = 'T';
			list[i].grid[x][y].visited = true;
			// if x, y picked is the one which is starting or ending point, so
			// pick another one
			do {
				x = (int) Math.floor(Math.random() * list[i].grid.length);
				y = (int) Math.floor(Math.random() * list[i].grid[0].length);
			} while (list[i].grid[x][y].visited);
			// after getting x and y, you would need to get the spot and get it
			// started in initializing the values of it with the 30% blocked
			grid = list[i].grid;
			initialize_grid(x, y);

			/*
			 * TO PRINT OUT THE CELL WHICH WAS CREATED!!!!!!, YOU CAN GET RID OF
			 * THIS IF YOU WOULD LIKE
			 */
			//list[i].print();
			BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            String name = "temp"+i;
	            File logFile = new File(name);

	            // This will output the full path where the file will be written to...
	            System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	            writer.write(list[i].toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
			System.out.println("---------------------------------------------------------");
		}

	}
}
