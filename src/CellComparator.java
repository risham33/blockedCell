import java.util.Comparator;
/**
 * Sangini Shah (sms591) and Risham Chokshi (ryc19)
 * 
 * Compares two cells based on f values. In the case of a tie, it returns 0.
 *  
 * @author Sangini
 *
 */
public class CellComparator implements Comparator<Cell> {

	public int compare(Cell cell1, Cell cell2){
			
		if(cell1.f < cell2.f)
			return -1;
		else if(cell1.f > cell2.f)
			return 1;
		else
			return 0;
	}
}
