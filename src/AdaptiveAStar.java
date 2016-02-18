import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
/*
 * Sangini Shah (sms591) and Risham Chokshi (ryc19)
 * 
 * Implementation of Adaptive A* 
 */
public class AdaptiveAStar {
	
	public static void main(String[] args) {
		Maze map = null; //map being searched
		Cell start = null;
		Cell goal = null;
		
		//Step 1: read in the maze
		map = readMaze();
		
		if(map == null){
			throw new NullPointerException("Null Maze Returned. Check if entered file name and n-value are correct.");
		}
		
		start = map.getStart();
		goal = map.getFinish();
		
		//Step 3: Compute multiple paths by repeating A*
		repeatAStar(map, start, goal);

	}
	
	/**
	 * Reads in the maze from the file and maps it into the Maze data structure
	 * 
	 * @return map	the maze generated from the file 
	 */
	public static Maze readMaze(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter maze file name: ");
		String file = scan.nextLine();
		System.out.print("Enter n (number of rows or columns): n = "); //Typically n=101 but kept line for debugging purposes
		int n = scan.nextInt();
		scan.close();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		   String line;
		   Maze m = new Maze(n); //the map that would be created
		   int x = 0; // x value
		   while ((line = br.readLine()) != null) {
		      // process the line
			  int y = 0; // the y value
			  for(int i=0;i<line.length();i++){
				  //Step 2: Initialize all cells, setting search = 0
				  if(line.charAt(i)!=' '){
					  m.grid[x][y].value = line.charAt(i);
					  if(line.charAt(i) == '1'){
						  m.grid[x][y].blocked = true;
		   			  } else if (line.charAt(i) == '0'){
		   				  m.grid[x][y].blocked = false;
		   			  } else if (line.charAt(i) == 'A'){
		   				  m.start_x = x;
		   				  m.start_y = y;
		   			  } else if (line.charAt(i) == 'T'){
		   				  m.end_x = x;
		   				  m.end_y = y;
		   			  }
		   			  y++;
		   		  }
		   	  }
			  x++; //the x value increases
		   }
		   //System.out.println(m.toString()); //printout of the map
		   return m;
		}
		catch(Exception e){
			System.out.println("Error in Reading Maze.");
			return null;
		} 
	}
	
	/**
	 * Driver method that runs A* with different start states depending on the path returned by computePath(). 
	 * Initially begins with the start position in the map. Outputs whether or not there is a solution for the Maze, and if there
	 * is, the path is printed out.
	 * 
	 * @param map	Maze data structure produced by reading in the file
	 * @param start	Start position in the map
	 * @param goal	Finish position in the map
	 */
	public static void repeatAStar(Maze map, Cell start, Cell goal){
 		PriorityQueue<Cell> open = null; //list of open nodes 
		LinkedList<Cell> closed = null; //list of closed nodes
		LinkedList<Cell> finalPath = new LinkedList<Cell>(); //list of all cells' in order in the final path
		Cell[] subTree = null; //path created through a run of A*
		int aStarCount = 0; //number of times A* has been computed
		ArrayList<Cell> blocked = new ArrayList<Cell>(); //cells we have knowledge of being blocked
		int numExpanded = 0; //total number of cells that have been expanded
		
		//Traverse through maze until target is reached
		while(!start.equals(goal)){
			aStarCount++;
			start.g = 0;
			start.h = Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
			start.f = start.g + start.h;
			start.search = aStarCount;
			goal.g = Integer.MAX_VALUE;
			goal.h = 0;
			goal.search = aStarCount;
			open = new PriorityQueue<Cell>(map.grid.length * map.grid.length, new BigGComparator()); 
			closed = new LinkedList<Cell>();
			open.add(start);
			if(map.getNorth(start) != null && map.getNorth(start).blocked && !blocked.contains(map.getNorth(start))){
				blocked.add(map.getNorth(start));
			}
			if(map.getEast(start) != null && map.getEast(start).blocked && !blocked.contains(map.getEast(start))){
				blocked.add(map.getEast(start));
			}
			if(map.getSouth(start) != null && map.getSouth(start).blocked && !blocked.contains(map.getSouth(start))){
				blocked.add(map.getSouth(start));
			}
			if(map.getWest(start) != null && map.getWest(start).blocked && !blocked.contains(map.getWest(start))){
				blocked.add(map.getWest(start));
			}
			
			//Step 4: Use A* to find a path from start to goal
			subTree = computePath(start, goal, map, open, closed, blocked, aStarCount);
			if(open.size() == 0){
				numExpanded += closed.size();
				System.out.println(map.toString());
				System.out.println("Target Unreachable.");
				System.out.println("Number of Expanded Cells: " + numExpanded);
				return;
			}
			
			//Step 5: Follow the path traced by A* to see if a conclusion has been reached
			Stack<Cell> path = new Stack<Cell>();
			path.push(goal);
			int index = goal.x * map.grid.length + goal.y;
			while(index != start.x * map.grid.length + start.y){
				Cell parent = subTree[index];
				path.push(parent);
				index = parent.x * map.grid.length + parent.y;
			}
			Cell successor = null;
			Cell current = path.pop();
			while(!path.isEmpty() && !current.equals(goal) && !current.blocked){
				successor = current;
				current = path.pop();
				//Keep track of the path that is being used to trace to target
				if(finalPath.isEmpty() || !finalPath.contains(successor)){
					finalPath.add(successor);
				} else {
					//Cell is already in the path, implying that the route through it is being rerouted. 
					//Wipe all of the nodes resulting from that node so the new path originating from the state can be traced
					Iterator<Cell> iter = finalPath.iterator();
					boolean foundSucc = false;
					while(iter.hasNext()){
						Cell c = iter.next();
						if(c.equals(successor)){
							foundSucc = true;
						} else if (foundSucc){
							iter.remove();
						}
					}
				}
			}
			//Some cell on determined path was blocked. Set start point to be the one preceding it and redo A*
			if(!current.equals(goal)){
				if(successor != null){
					start = successor;
				}
				numExpanded += closed.size(); //count number of expanded nodes
			} 
			//Found the path! Print out the route
			else { 
				numExpanded += closed.size();
				Iterator<Cell> iter = finalPath.iterator();
				while(iter.hasNext()){
					Cell c = iter.next();
					if(!c.equals(map.getStart()))
						c.value = '*';
				}
				System.out.println(map.toString());
				System.out.println("Target Reached. See map above for generated route.");
				System.out.println("Number of Expanded Cells: " + numExpanded);
				return;
			}
		}
	}

	/**
	 * This is the A* algorithm that traces a path from the given start position to the given goal with the known information of the Maze.
	 * 
	 * @param start	Start state
	 * @param goal	End state
	 * @param map	Maze being solved
	 * @param open	Priority Queue of open cells ordered via a Cell Comparator
	 * @param closed	List of closed/expanded nodes
	 * @param blocked	List of all the cells we currently know to be blocked in the maze
	 * @param counter	Count of which A* search we are running
	 * @return subTree	Array that keeps track of the parent pointers of all cells explored in this A* search		
	 */
	private static Cell[] computePath(Cell start, Cell goal, Maze map, PriorityQueue<Cell> open, LinkedList<Cell> closed, ArrayList<Cell> blocked, int counter) {
		/*
		 * This tree is maintained as an array in order to save lookup times. The index, identifying the child node, is calculated
		 * by using the equation x*n + y where (x,y) are the cell coordinates and n is the dimension of the grid. The cell found at
		 * tree[x*n + y] would be the parent of cell (x,y).
		 * For example:
		 * 	Cell (3,4) on a 5 by 5 grid has parent cell temp <=> tree[19] = temp  
		 */
		Cell[] tree = new Cell[map.grid.length * map.grid.length];
		
		//Run until goal is found or there are no more open cells to expand
		while(open.peek() != null && goal.g > open.peek().f){
			Cell state = open.remove();
			closed.add(state);
			Cell north = map.getNorth(state);
			Cell east = map.getEast(state);
			Cell south = map.getSouth(state);
			Cell west = map.getWest(state);
			Cell[] actions = new Cell[4];
			
			//Check which actions are valid and add them to the list
			if(north != null && !blocked.contains(north)){
				actions[0] = north;
			}
			if(east != null && !blocked.contains(east)){
				actions[1] = east;
			}
			if(south != null && !blocked.contains(south)){
				actions[2] = south;
			}
			if(west != null && !blocked.contains(west)){
				actions[3] = west;
			}
			
			for(int i = 0; i < 4; i++){
				Cell succ = actions[i];
				if(succ != null){
					//Cell has not be explored yet
					if(succ.search < counter){
						succ.g = Integer.MAX_VALUE;
						succ.search = counter;
					}
					//Shorter path found to succ
					if(succ.g > (state.g + 1)){
						succ.g = state.g + 1;
						if(!succ.equals(tree[state.x * map.grid.length + state.y])) //make sure to avoid loops in tree
							tree[succ.x * map.grid.length + succ.y] = state;
						if(open.contains(succ)){
							open.remove(succ);
						}
						if(succ.hNew == Integer.MAX_VALUE){
							succ.h = Math.abs(succ.x - goal.x) + Math.abs(succ.y - goal.y);
							succ.f = succ.h + succ.g;
						} else {
							succ.f = succ.hNew + succ.g;
						}
						open.add(succ);
					}
				}
			}
		}
		//Update all expanded cells' h values for Adaptive A* Search
		if(closed.size() != 0){
			Iterator<Cell> iter = closed.iterator();
			while(iter.hasNext()){
				Cell c = iter.next();
				c.hNew = goal.g - c.g;
			}
		}
		return tree; //return tree with potential path
	}

}
