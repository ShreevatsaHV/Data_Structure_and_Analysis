package maze;

import java.util.*;
public class Cell{
   public Wall up, right, down, left; 
   public int x, y; 
   public List<Cell> adjacent; 
   public int rname; 
   public Cell prev; 

   public Cell(int x, int y){
      this.x = x;                     // row
      this.y = y;                     // column
      adjacent = new LinkedList<Cell>();
      prev = null;                    // prev is nothing initially
      rname = 0;
   }
}
