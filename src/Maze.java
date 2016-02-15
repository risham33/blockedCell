/*
 * THIS CLASS WOULD BE USED TO HOLD THE GENERATED 2D ARRAY OF THE dataStructure object
 */
public class Maze {
	
	Cell[][] grid;
	int start_x = 0;
	int start_y = 0;
	int end_x = 0;
	int end_y = 0;
	
	public Maze(int n){
		grid = new Cell[n][n];
		initialize_grid();
	}
	public void initialize_grid(){
		for(int i =0; i<grid.length;i++){
			for(int j=0 ; j<grid[i].length; j++){
				grid[i][j] = new Cell();
			}
		}
	}
	public String print(){
		String ret = "";
		for(int i =0; i<grid.length;i++){
			for(int j=0 ; j<grid[i].length; j++){
				ret= ret + grid[i][j].value + " ";
			}
			ret = ret + "\n";
		}
		return ret;
	}
}
