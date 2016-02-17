import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Problem2 {
	
	public static void main(String[] args) {
		Maze map = null; //map being searched
		Cell start = null;
		Cell goal = null;
		
		//Step 1: read in the maze
		map = readMaze();
		
		start = map.getStart();
		goal = map.getFinish();
		
		//Step 3: Compute multiple paths by repeating A*
		repeatAStar(map, start, goal);

	}
	
	public static Maze readMaze(){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter maze file name: ");
		String file = scan.nextLine();
		System.out.print("Enter n (number of rows or columns): n = ");
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
		   System.out.println(m.toString()); //printout of the map
		   return m;
		}
		catch(Exception e){
			System.out.println("error");
			return null;
		} 
	}
	
	public static void repeatAStar(Maze map, Cell start, Cell goal){
 		PriorityQueue<Cell> open = null; //list of open nodes 
		LinkedList<Cell> closed = null; //list of closed nodes
		LinkedList<Cell> finalPath = new LinkedList<Cell>(); //list of all cells' in order in the final path
		Cell[] subTree = null; //path created through a run of A*
		int aStarCount = 0; //number of times A* has been computed
		ArrayList<Cell> blocked = new ArrayList<Cell>();
		int numExpanded = 0;
		
		while(!start.equals(goal)){
			aStarCount++;
			start.g = 0;
			start.h = Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
			start.f = start.g + start.h;
			start.search = aStarCount;
			goal.g = Integer.MAX_VALUE;
			goal.h = 0;
			goal.search = aStarCount;
			open = new PriorityQueue<Cell>(map.grid.length * map.grid.length, new CellComparator());
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
				System.out.println("Target Unreachable.");
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
				if(finalPath.isEmpty() || !finalPath.contains(successor)){
					finalPath.add(successor);
				} else {
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
			if(!current.equals(goal)){
				if(successor != null){
					start = successor;
				}
				numExpanded += closed.size();
			} else {
				Iterator<Cell> iter = finalPath.iterator();
				while(iter.hasNext()){
					Cell c = iter.next();
					if(!c.equals(map.getStart()))
						c.value = '*';
				}
				System.out.println("Target Reached: \n");
				System.out.println(map.toString());
				System.out.println("Number of Expanded Cells: " + numExpanded);
				return;
			}
		}
	}

	private static Cell[] computePath(Cell start, Cell goal, Maze map, PriorityQueue<Cell> open, LinkedList<Cell> closed, ArrayList<Cell> blocked, int counter) {
		Cell[] tree = new Cell[map.grid.length * map.grid.length];
		
		while(open.peek() != null && goal.g > open.peek().f){
			Cell state = open.remove();
			closed.add(state);
			Cell north = map.getNorth(state);
			Cell east = map.getEast(state);
			Cell south = map.getSouth(state);
			Cell west = map.getWest(state);
			Cell[] actions = new Cell[4];
			
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
					if(succ.search < counter){
						succ.g = Integer.MAX_VALUE;
						succ.search = counter;
					}
					if(succ.g > (state.g + 1)){
						succ.g = state.g + 1;
						if(!succ.equals(tree[state.x * map.grid.length + state.y]))
							tree[succ.x * map.grid.length + succ.y] = state;
						if(open.contains(succ)){
							open.remove(succ);
						}
						succ.h = Math.abs(succ.x - goal.x) + Math.abs(succ.y - goal.y);
						succ.f = succ.h + succ.g;
						open.add(succ);
					}
				}
			}
		}
		return tree;
	}

}
