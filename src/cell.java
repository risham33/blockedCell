/*
 * THIS CLASS WOULD BE USED TO HOLD THE GENERATED 2D ARRAY OF THE dataStructure object
 */
public class cell {
	
	dataStructure[][] grid;
	public cell(int n){
		grid = new dataStructure[n][n];
		initialize_grid();
	}
	public void initialize_grid(){
		for(int i =0; i<grid.length;i++){
			for(int j=0 ; j<grid[i].length; j++){
				grid[i][j] = new dataStructure();
			}
		}
	}
	public void print(){
		for(int i =0; i<grid.length;i++){
			for(int j=0 ; j<grid[i].length; j++){
				System.out.print(grid[i][j].value + " " );
			}
			System.out.println();
		}
	}
}
