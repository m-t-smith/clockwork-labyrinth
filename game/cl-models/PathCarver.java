package game.main;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;                         
import game.main.Room;

public class PathCarver {
   
    private int[][] map;
    private Room start;
    private Stack<Point> path = new Stack();
    private Point currentCell;
    
    public PathCarver(int[][] map, Room start){
        
        this.map = map;
        this.start = start;
    }
    
    
    public void startCarving(int[][] map, Room start){
        
        Point currentCell = new Point(start.getX(), (start.getY() + (start.getHeight()
                /2)));
        int[] badNeighbors = {4, 4, 4, 4};
        if(map[(int) currentCell.getY()][(int) currentCell.getX() - 2] != 2){ //check if blocked
            currentCell.translate((start.getWidth() - 1), 0); //try opposite side of room
        }
        map[currentCell.y][currentCell.x] = 0;
       // System.out.println(currentCell.getY() + " " + currentCell.getX());
        carve(currentCell, map, badNeighbors, false);
    }
    public boolean checkCellValid(Point cell, int[][] map){
    
        int rightBound = map.length - 1;
        int bottomBound = map[0].length -1; 
        boolean cellValid = false;
    
        if(cell.x > 0 && cell.x < rightBound){
           if(cell.y > 0 && cell.y < bottomBound){
                if(map[cell.y][cell.x] == 2){
                 
                    cellValid = true;
                }
            }
        }
        return cellValid;
    }
    
    public boolean carve(Point currentCell, int[][] map, int[] badNeighbors, boolean finished){
        
        Random rand = new Random();
        int direction = rand.nextInt(4);
        boolean valid;
        int backtrack = 0;
        if(!finished){
            for(int i = 0; i < 4; i++){
                backtrack += badNeighbors[i];
                if (badNeighbors[i] == direction){
                    if(backtrack == 7){
                        direction = 3;
                    } else 
                    if(backtrack == 8){
                        direction = 2;
                    } else 
                    if(backtrack == 9 && badNeighbors[1] == 4){
                        direction = 1;
                    } else {
                        for(int j = 0; j < 4; j++){
                            if(badNeighbors[j] == 4){
                                direction = j;
                            }
                        }
                    }
                }
            }
            if(backtrack == 6){
                for(int i = 0; i < 4; i++){
                    badNeighbors[i] = 4;
                }
                if(!path.empty()){
                currentCell = path.pop();
                carve(currentCell, map, badNeighbors, false);
                } else {
                    
                    finished = true;
                    return finished;
                }
            }


            switch(direction){

                case(0):
                    Point cellUp = new Point(currentCell.x, currentCell.y - 2);
                    valid = checkCellValid(cellUp, map);
                    if(valid){
                        path.push(currentCell);
                        currentCell = cellUp;
                        map[currentCell.y][currentCell.x] = 0;
                        map[currentCell.y + 1][currentCell.x] = 0;
                        for(int i = 0; i < 4; i++){
                            badNeighbors[i] = 4;
                        }
                        badNeighbors[2] = 2;
                        carve(currentCell, map, badNeighbors, false);
                        break;
                    } else {
                        badNeighbors[0] = 0;
                     //   carve(currentCell, map, badNeighbors, false);
                    }
                

                case(1):

                    Point cellLeft = new Point(currentCell.x - 2, currentCell.y);
                    valid = checkCellValid(cellLeft, map);
                    if(valid){
                        path.push(currentCell);
                        currentCell = cellLeft;
                        map[currentCell.y][currentCell.x] = 0;
                        map[currentCell.y][currentCell.x + 1] = 0;
                        for(int i = 0; i < 4; i++){
                            badNeighbors[i] = 4;
                        }
                        badNeighbors[3] = 3;
                        carve(currentCell, map, badNeighbors, false);
                        break;
                    } else {
                        badNeighbors[1] = 1;
                     //   carve(currentCell, map, badNeighbors, false);
                    }                               
                

                case(2):
                    Point cellDown = new Point(currentCell.x, currentCell.y + 2);
                    valid = checkCellValid(cellDown, map);
                    if(valid){
                        path.push(currentCell);
                        currentCell = cellDown;
                        map[currentCell.y][currentCell.x] = 0;
                        map[currentCell.y - 1][currentCell.x] = 0;
                        for(int i = 0; i < 4; i++){
                            badNeighbors[i] = 4;
                        }
                        badNeighbors[0] = 0;
                        carve(currentCell, map, badNeighbors, false);
                        break;
                    } else {
                        badNeighbors[2] = 2;
                     //   carve(currentCell, map, badNeighbors, false);
                    }   

                

                case(3):

                    Point cellRight = new Point(currentCell.x + 2, currentCell.y);
                    valid = checkCellValid(cellRight, map);
                    if(valid){
                        path.push(currentCell);
                        currentCell = cellRight;
                        map[currentCell.y][currentCell.x] = 0;
                        map[currentCell.y][currentCell.x - 1] = 0;
                        for(int i = 0; i < 4; i++){
                            badNeighbors[i] = 4;
                        }
                        badNeighbors[1] = 1;
                        carve(currentCell, map, badNeighbors, false);
                        break;
                    } else {

                        badNeighbors[3] = 3;
                        carve(currentCell, map, badNeighbors, false);
                    }   
                
            }
        } 
        return finished;
    }
    
}
