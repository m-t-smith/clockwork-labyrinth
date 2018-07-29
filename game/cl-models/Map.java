package game.main;

import java.util.Arrays;

public class Map{
    private int[][] mapArray;
    private int mapSideLength;
    
    public Map(int[][] myMap, int mapSideLength){
        this.mapArray = myMap;
        this.mapSideLength = mapSideLength;
    }
    public int[][] getMap(){
        return mapArray;
    }
    public void setMap(int[][] myMap){
        this.mapArray = myMap;
    }
    public int getMapSideLength(){
        return mapSideLength;
    }
    public void setMapSideLength(int mapSideLength){
        this.mapSideLength = mapSideLength;
    }
   
    @Override
    public String toString(){
        String string = new String();
        int[] rowIndx = new int[mapArray.length];
        int[] colIndx = new int[mapArray[0].length];
        for(int i = 0; i < colIndx.length; i++){
            colIndx[i] = i;
        }
        string = "I" + Arrays.toString(colIndx) + "\n";
        for(int i = 0; i < rowIndx.length; i++){
            rowIndx[i] = i; 
        }
        int i = 0;
        for(int[] rows: mapArray){
           string += colIndx[i] + (Arrays.toString(rows)) + "\n";
           i++;
        }
        return string;
    } 
}
