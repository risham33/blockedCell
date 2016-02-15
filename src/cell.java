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
}
