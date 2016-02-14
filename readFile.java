public class readFile{
  public static void main(String [] args){
    
      String file = "temp0.txt";
	
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    cell map = new cell(101); //the map that would be created
		    int x = 0; // x value
		    while ((line = br.readLine()) != null) {
		       // process the line. 
		    	int j = 0; // the y value
		    	for(int i=0;i<line.length();i++){
		    		if(line.charAt(i)!=' '){
		    			map.grid[x][j].value = line.charAt(i);
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
