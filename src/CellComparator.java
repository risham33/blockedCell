import java.util.Comparator;

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
