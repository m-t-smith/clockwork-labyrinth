package game.main;

public class Room {
   
   private int x;
   private int y;
   private int[][] roomArray;
   private int width;
   private int height;
    
    public Room(int y, int x, int width, int height){
        this.x = x;
        this.y = y;
        this.roomArray = new int[width][height];
        this.width = width;
        this.height = height;
    
    }
    public int[][] getRoomArray(){
        return roomArray;
    }
    public void setRoomArray(int[][] dimensions){
        this.roomArray = dimensions;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }

}
