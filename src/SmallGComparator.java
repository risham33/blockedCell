import java.util.Comparator;

/**
 * Compares two cells based on f values. In the case of a tie, it gives priority
 * to the cell with a smaller g value and if that is also the same then it returns 0.
 *  
 * @author Sangini
 *
 */
public class SmallGComparator implements Comparator<Cell> {
	public int compare(Cell cell1, Cell cell2){
			
		if(cell1.f < cell2.f){
			return -1;
		}
		else if(cell1.f > cell2.f){
			return 1;
		}
		else{
			if(cell1.g < cell2.g){
				return -1;
			} else if(cell1.g > cell2.g){
				return 1;
			} else {
				return 0;
			}
		}
	}
}
