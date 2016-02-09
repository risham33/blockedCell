
public class Main {
	static dataStructure [][] grid;
	
	public static void initialize_grid(int x, int y){
		
		if(x>=0 && x<grid.length && y>=0 && y<grid.length && grid[x][y].visted!=true){
			
			grid[x][y].visted = true;
			//all the possible blocks around it
			if(x>=0 && x<grid.length && y-1>=0 && y-1<grid.length && grid[x][y-1].visted!=true)
				initialize_grid(x,y-1);
			if(x>=0 && x<grid.length && y+1>=0 && y+1<grid.length && grid[x][y+1].visted!=true)
				initialize_grid(x,y+1);
			if(x+1>=0 && x+1<grid.length && y+1>=0 && y+1<grid.length && grid[x+1][y+1].visted!=true)
				initialize_grid(x+1,y+1);
			if(x+1>=0 && x+1<grid.length && y-1>=0 && y-1<grid.length && grid[x+1][y-1].visted!=true)
				initialize_grid(x+1,y-1);
			if(x+1>=0 && x+1<grid.length && y>=0 && y<grid.length && grid[x+1][y].visted!=true)
				initialize_grid(x+1,y);
			if(x-1>=0 && x-1<grid.length && y-1>=0 && y-1<grid.length && grid[x-1][y-1].visted!=true)
				initialize_grid(x-1,y-1);
			if(x-1>=0 && x-1<grid.length && y+1>=0 && y+1<grid.length && grid[x-1][y+1].visted!=true)
				initialize_grid(x-1,y+1);
			if(x-1>=0 && x-1<grid.length && y>=0 && y<grid.length && grid[x-1][y].visted!=true)
				initialize_grid(x-1,y);
			
			if(((int) Math.floor(Math.random() * 101)) <= 30){
				grid[x][y].blocked = true;
				grid[x][y].value = 'O';
			}
		}
		
		return;
	}
	public static void main(String[] args){
		//its the list of all the 50 maps that we have
		cell[] list = new cell[50];
		//creating those cells and initializing their grids
		for(int i=0; i<list.length;i++){
			list[i] = new cell(101);
			int x = (int) Math.floor(Math.random() * list[i].grid.length);
			int y = (int) Math.floor(Math.random() * list[i].grid[0].length);
			//after getting x and y, you would need to get the spot and get it 
			//started in initializing the values of it with the 30% blocked
			grid = list[i].grid;
			initialize_grid(x,y);
			/*
			 * TO PRINT OUT THE CELL WHICH WAS CREATED!!!!!!, YOU CAN GET RID OF THIS IF YOU WOULD LIKE
			 */
			list[i].print();
			System.out.println("---------------------------------------------------------");
		}
		

	}
}
