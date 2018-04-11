package game.main;

import java.awt.Point;
import java.awt.Rectangle;


public class TileManager {
    private Handler handler;
    private Point[] twoDPoints; //maybe declare as list and import java..list
    public Point[] isoPoints;
    private Point playerSpot;
    public Rectangle renderBox;
    private int recX;
    private int recY;
   
    
    public TileManager(Handler handler, int[][] twoDMap, Room room) {
        
        this.handler = handler;
        playerSpot = placeUnit(room);
        twoDPoints = new Point[twoDMap.length];
        isoPoints = new Point[twoDPoints.length];
        recX =  playerSpot.x - Game.WIDTH/2;  
        recY =  playerSpot.y - Game.HEIGHT/2;                
   
        renderBox = new Rectangle(recX, recY, Game.WIDTH, Game.HEIGHT);
        twoDPoints = make2DpointArray(twoDMap);
       
        isoPoints = makeIsoPointArray(twoDPoints);
    
        placeTiles(isoPoints, twoDPoints, twoDMap);
        showTiles();
    }
    public Point placeUnit(Room room){
        
        Point twoDSpot = new Point(Game.TILE_SIZE*(room.getX() + (room.getWidth())/2),
                            Game.TILE_SIZE*(room.getY() + (room.getHeight())/2));
        Point isoSpot = twoDtoIso(twoDSpot);
        isoSpot.setLocation(isoSpot.x, isoSpot.y);
        
        return isoSpot;
    }
    public Point getPlayerSpot(){
        return playerSpot;
    }
   
    
    public Point isoTo2D(Point pt){
        Point tempPt = new Point(0,0);
        tempPt.x = (int) (2 * pt.getY() + pt.getX()) / 2;
        tempPt.y = (int) (2 * pt.getY() - pt.getX()) / 2;
        return(tempPt);
    }
    
    private Point[] make2DpointArray(int[][] twoDMap){
        int k = 0;
        int size = twoDMap.length*twoDMap.length;
        Point[] twoDPoints = new Point[size];
        while(k < size ){
            for(int i = 0; i < twoDMap.length ; i++){
                for(int j = 0; j < twoDMap.length ; j++){
                    twoDPoints[k] = new Point((j*Game.TILE_SIZE),(i*Game.TILE_SIZE));
                   k++;
                }

            }
        }
      
        return twoDPoints;
    }
    protected Point twoDtoIso(Point pt){
           
        try{
            if(pt != null){
              int x = (int) (pt.getX() - pt.getY());
              int y = (int) ((pt.getX() + pt.getY()) / 2);
              Point tempPt = new Point(x, y);  
              return tempPt;
            } 
     
        } catch (NullPointerException nullPointer){
            System.out.println( nullPointer + "nullPoint line 54");
        }
        return null;
    }
    
    private Point[] makeIsoPointArray(Point[] twoDP){
        Point[] isoPoints = new Point[twoDP.length];
      //  System.out.println("twoDP size" + twoDP.length);
        for(int i = 0; i < twoDP.length; i++){
            Point isoPoint = new Point();
            Point twoDPoint = twoDP[i];
        //    System.out.println("2DP: " + twoDPoint.getX() + " " + twoDPoint.getY());
            isoPoint = twoDtoIso(twoDPoint);
        //    System.out.println("iso Point " + i + ": " + isoPoint.getX()+" "+ isoPoint.getY());
            isoPoints[i] = isoPoint;
           
        } 
        return  isoPoints;
    }
     
    private void placeTiles(Point[] isoPoints, Point[] twoDPoints, int[][] twoDMap){
        int k = 0;
        int size = (int) twoDMap.length*twoDMap.length;

        
        while(k < size){
                Point twoDPoint = twoDPoints[k];
                Point isoPoint = isoPoints[k];
              //  System.out.println(isoPoint.x + " " + isoPoint.y + "line 107");
                k++;
            try{
                if(twoDMap[(int)Math.floor(twoDPoint.getY()/Game.TILE_SIZE)][(int)Math.floor(twoDPoint.getX()/Game.TILE_SIZE)] == 0){
                        handler.addObject(new DTile((int)isoPoint.getX(), 
                                (int)isoPoint.getY(), ID.DirtTileSquare));
                    } else {
                        handler.addObject(new RTile((int)isoPoint.getX(), 
                                (int)isoPoint.getY(), ID.RockTileSquare));
                        }  
                } catch(NullPointerException nullPointer) {
                    System.out.println( nullPointer + "NullPoint caught line 85");
                }
            
        }
    }
    
    private void showTiles(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObj = handler.object.get(i);
            
                if(renderBox.intersects(tempObj.getBounds())){
                        tempObj.setIsVIsible(true);
                } else {
                        tempObj.setIsVIsible(false);
                }
            
            
        }
    
}
    
    public void render(){
        
        
    }
    public void tick(GameObject player){
        
        playerSpot = new Point(player.x, player.y);
        recX = (int) playerSpot.x - Game.WIDTH/2 - Game.TILE_SIZE;
        recY = (int) playerSpot.y - Game.HEIGHT/2 - Game.TILE_SIZE;
        renderBox = new Rectangle(recX, recY, Game.WIDTH + Game.TILE_SIZE*2,
                Game.HEIGHT + Game.TILE_SIZE);
  
        showTiles();
       
            
    }
    

}
