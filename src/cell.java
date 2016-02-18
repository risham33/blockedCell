import java.util.Comparator;

/*
 * THIS CLASS WOULD BE THE OBJECT CLASS OF THE WHOLE ENTIRE PROGRAM, WHICH WOULD CONTAIN IF THE 
 * FOLLOWING BLOCK IS VISITED OR NOT
 * IF THE OBJECT IS BLOCKED THEN THE VALUE OF THAT OBJECT WOULD BE CHANGED TO '1', IF IT IS NOT BLOCKED
 * THEN IT WOULD BE '0' 
 * If it is the start point, then it would be changed to A and then if it is an end point then it would 
 * changed to T
 */
public class Cell {
	boolean visited = false;
	boolean blocked = false;
	char value = '0';
	int g = Integer.MAX_VALUE; //g-value, shortest path length from start to this cell
	int h = Integer.MAX_VALUE; //h-value, heuristic for this cell --> WILL BE MANHATTAN DISTANCE TO GOAL
	int hNew = Integer.MAX_VALUE; //new h-value heuristic for Adaptive A*
	int f = g + h; //f-value, to be used to pick next OPEN cell
	int search = 0; //search value
	int x; //x coordinate
	int y; //y coordinate
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object c1, Object c2){
		if(c1 == null || c2 == null)
			return false;
		
		Cell cell1 = (Cell) c1;
		Cell cell2 = (Cell) c2;
		
		if(cell1.x == cell2.x && cell1.y == cell2.y)
			return true;
		
		return false;
	}
	
	public int compareTo(Object c){
		if(c == null)
			throw new NullPointerException();
		
		Cell cell = (Cell) c;
		
		if(this.f < cell.f)
			return -1;
		else if(this.f > cell.f)
			return 1;
		else
			return 0;
	}
	
	public String toString(){
		return (x * 101 + y) + "";
	}
}
