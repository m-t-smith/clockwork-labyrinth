package game.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class PathAI {
    
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;
    
    int[][] myMap;
    ArrayList<Point> obstacles;
    int mapHeight;
    int mapWidth;
    int startingX;
    int startingY;
    int destinationX;
    int destinationY;
    Stack<Point> path;
    Cell [][] grid;
    
    static class Cell{  
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent; 
        
        Cell(int i, int j){
            this.i = i;
            this.j = j; 
        }
        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
        
    }
    static boolean closed[][];
    
    static PriorityQueue<Cell> open;

    
    public PathAI(int[][] map, Point start, Point end){
        this.myMap = map;
        this.mapHeight = map.length;
        this.mapWidth = map[0].length;
        obstacles = new ArrayList();
        grid = new Cell[mapHeight][mapWidth];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
        Cell c1 = (Cell)o1;
        Cell c2 = (Cell)o2;
        return c1.finalCost<c2.finalCost?-1:
                c1.finalCost>c2.finalCost?1:0;
        });
        closed = new boolean[mapHeight][mapWidth];
        path = new Stack();
        
        setStartCell(start.y, start.x);
        setEndCell(end.y, end.x);
        
        fillGrid();
        findPath();
        
        if(closed[destinationY][destinationX]){
                Point currentP;
                Point nextP;
                Cell current = grid[destinationY][destinationX];
                currentP = new Point(current.j, current.i);
                path.push(currentP);
                //System.out.print(current);
                while(current.parent!=null){
                 //   System.out.print(" -> "+current.parent);
                    nextP = new Point(current.parent.j, current.parent.i);
                    path.push(nextP);
                    current = current.parent;
                } 
        }
//         System.out.println("\nScores for cells: ");
//           for(int i=0;i<mapWidth;++i){
//               for(int j=0;j<mapWidth;++j){
//                   if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
//                   else System.out.print("BL  ");
//               }
//               System.out.println();
//           }
//           System.out.println();
    }
    
    public Point getnextPoint(){
        if(!path.empty()){
        Point nextPoint = path.pop();
        
        return nextPoint;
        } else{
            return null;
        }
    }
    
    public Stack getPath(){
        if(path != null){
            return path;
        } else {
            System.out.println("no path");
            return new Stack();
        }
    }
    
    private void fillGrid( ){
        
        for(int i = 0; i < mapHeight; i++){  //
            for(int j = 0; j < mapWidth; j++){
                if(myMap[i][j] == 1 ){
                    obstacles.add(new Point(j,i));
                }
            }
        }
           
           for(int i=0;i<mapHeight;++i){
              for(int j=0;j<mapWidth;++j){
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-destinationY)+Math.abs(j-destinationX);
              //    System.out.printf(grid[i][j].heuristicCost);
                  
              }
           //   System.out.println(grid[i].length);
          //   System.out.println();
           }
           grid[startingY][startingX].finalCost = 0;
           
           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
           for(int i=0;i<obstacles.size();++i){
               setBlocked(obstacles.get(i).y, obstacles.get(i).x);
           }
        
    }
    
            
    public void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    private void setStartCell(int i, int j){
        startingY = i;
        startingX = j;
    }
    
    private void setEndCell(int i, int j){
        destinationY = i;
        destinationX = j; 
    }
    
    private void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null|| closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
    
    private void findPath(){ 
        
        //add the start location to open list.
    //    System.out.println(grid[startingY][startingX]);
    try{
        open.add(grid[startingY][startingX]);
    } catch(Exception e) {
        System.out.println(e.getStackTrace());
    }
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current == null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[destinationY][destinationX])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }  
            }
        } 
    }

}
