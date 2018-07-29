package game.main;

public class Room {
   
   private int x;
   private int y;
   private int[][] roomArray;
   private int width;
   private int height;
   private String id;
    
    public Room(int y, int x, int width, int height){
        this.x = x;
        this.y = y;
        this.roomArray = new int[width][height];
        this.width = width;
        this.height = height;
        this.id = "X" + x + "Y" + y + "W" + width + "H" + height;
    
    }
    public int[][] getRoomArray(){
        return this.roomArray;
    }
    public void setRoomArray(int[][] dimensions){
        this.roomArray = dimensions;
    }
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getWidth(){
        return this.width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return this.height;
    }
    public void setRoomID(String s){
        this.id = s;
    }
    public String getRoomID(){
        return this.id;
    }
    public void setHeight(int height){
        this.height = height;
    }

}
