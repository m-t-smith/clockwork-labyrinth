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
        for(int[] rows: mapArray){
           string += (Arrays.toString(rows)) + "\n";
        }
        return string;
    } 
}
