package maze;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JPanel {

    private Cell[][] cell;
    private ArrayList<Wall> walls; 
    private Random rand;
    private int height;
    private int width;
    private int no;
    private DisjSets ds;
    private int xaxis; 
    private int yaxis;
    private int cellSize;
    private int randomWall;
	
    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        cell = new Cell[height][width];
        walls = new ArrayList<Wall>((height - 1) * (width - 1));
        generateMaze();
        setPreferredSize(new Dimension(800, 700));
   }

    private void generatecell() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {               
                cell[i][j] = new Cell(i, j);
                if (i == 0) {
                    cell[i][j].up = new Wall(cell[i][j]);
                } 
                else 
                {
                    cell[i][j].up = new Wall(cell[i - 1][j], cell[i][j]);
                    walls.add(cell[i][j].up);
                }
                if (i == height - 1) {
                    cell[i][j].down = new Wall(cell[i][j]);
                }
                
                if (j == 0) {
                    cell[i][j].left = new Wall(cell[i][j]);
                } 
                else 
                {
                    cell[i][j].left = new Wall(cell[i][j - 1], cell[i][j]);
                    walls.add(cell[i][j].left);
                }
                if (j == width - 1) {
                    cell[i][j].right = new Wall(cell[i][j]);
                }
                cell[i][j].rname = cellNum++;
            }
        }
        
        // initalizing entrance and exit
        cell[0][0].up.removed = true;
        cell[0][0].rname = 0;
        cell[height - 1][width - 1].right.removed = true;
        cell[height - 1][width - 1].rname = (height * width);
    }
    
    private void generateMaze() {
        generatecell();
        ds = new DisjSets(width * height);
        rand = new Random(); 
        no = width * height;

        while (no > 1) {
            randomWall = rand.nextInt(walls.size());           // random wall
            Wall temp = walls.get(randomWall);
            
            int cellA = temp.presentCell.y + temp.presentCell.x * (width);       //1st random cell
            int cellB = temp.nextCell.y + temp.nextCell.x * (height);            //2nd random cell

            if (ds.find(cellA) != ds.find(cellB)) {
                walls.remove(randomWall);
                ds.union(ds.find(cellA), ds.find(cellB));
                temp.removed = true;
                no--;
            }
        }
    }

    private int cellNum = 0;
	
	 public void paintComponent(Graphics g) {
        xaxis = 10;
        yaxis = 10;
        
        cellSize = (height)/height+10;

        int x = xaxis;
        int y = yaxis;

        for (int i = 0; i <= height - 1; i++) {
            for (int j = 0; j <= width - 1; j++) {
                if (!(cell[i][j].up.removed)) {
                    g.drawLine(x, y, x + cellSize, y);
                }

                if (cell[i][j].left.removed == false) {
                    g.drawLine(x, y, x, y + cellSize);
                }
                if ((i == height - 1) && cell[i][j].down.removed == false) {
                    g.drawLine(x, y + cellSize, x + cellSize,y + cellSize);
                }
                if ((j == width - 1) && cell[i][j].right.removed == false) {
                    g.drawLine(x + cellSize, y, x + cellSize,y + cellSize);
                }
                x = x+cellSize;
            }
            x = xaxis;
            y = y+cellSize;
        }
   }
   
   public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        int a, b;
        System.out.print("Enter the size of your maze: ");
        a = input.nextInt();
        if(a<2){
        	System.out.println("Enter a value greater than 1");
        	System.exit(0);
        }
        b = input.nextInt();
        if(b<2){
        	System.out.println("Enter a value greater than 1");
        	System.exit(0);
        }
        input.close();
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.getContentPane().add(new Maze(a, b));
        frame.pack();
        frame.setVisible(true);
    }
}