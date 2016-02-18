/*
 * Sangini Shah (sms591) and Risham Chokshi (ryc19)
 * 
 * THIS CLASS WOULD BE USED TO HOLD THE GENERATED 2D ARRAY OF THE Cell object
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
				grid[i][j] = new Cell(i, j);
			}
		}
	}
	
	public Cell getStart(){
		return grid[start_x][start_y];
	}
	
	public Cell getFinish(){
		return grid[end_x][end_y];
	}
	
	public Cell getNorth(Cell c){
		if(c == null)
			return null;
		
		if((c.x - 1) < 0)
			return null;
		else
			return grid[c.x -1][c.y];
	}
	
	public Cell getEast(Cell c){
		if(c == null)
			return null;
		
		if((c.y + 1) >= grid.length)
			return null;
		else
			return grid[c.x][c.y + 1];
	}
	
	public Cell getSouth(Cell c){
		if(c == null)
			return null;
		
		if((c.x + 1) >= grid.length)
			return null;
		else
			return grid[c.x + 1][c.y];
	}
	
	public Cell getWest(Cell c){
		if(c == null)
			return null;
		
		if((c.y - 1) < 0)
			return null;
		else
			return grid[c.x][c.y - 1];
	}
	
	public String toString(){
		String ret = "";
		for(int i=0; i<grid.length;i++){
			for(int j=0 ; j<grid[i].length; j++){
				ret= ret + grid[i][j].value + " ";
			}
			ret = ret + "\n";
		}
		return ret;
	}
}
