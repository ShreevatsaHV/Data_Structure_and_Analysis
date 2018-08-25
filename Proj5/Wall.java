package maze;

public class Wall{	   
	   public Cell presentCell, nextCell;
	   public boolean removed = false;

	   public Wall(Cell a, Cell b){
	      presentCell = a;
	      nextCell = b;
	   }

	   public Wall(Cell a){
	      presentCell = a;
	      nextCell = null;
	   }
}
