import java.io.BufferedReader;
import java.io.FileReader;

public class readFile{
  public static void main(String [] args){
    
      String file = "temp0.txt";
	
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    Maze map = new Maze(101); //the map that would be created
		    int x = 0; // x value
		    while ((line = br.readLine()) != null) {
		       // process the line. 
		    	int j = 0; // the y value
		    	for(int i=0;i<line.length();i++){
		    		if(line.charAt(i)!=' '){
		    			map.grid[x][j].value = line.charAt(i);
		    			if(line.charAt(i) == '1'){
		    				map.grid[x][j].blocked = true;
		    			} else {
		    				map.grid[x][j].blocked = false;
		    			}
		    			j++;
		    		}
		    	}
		    	x++; //the x value increase
		    	
		    }
		    System.out.println(map.print()); //printout of the map
		    
		}
		catch(Exception e){
			System.out.println("error");
		}  
  
  
  
  }

}
