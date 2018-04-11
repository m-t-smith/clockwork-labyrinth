/*
loops through game objects, renders to screen
 */
package game.main;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Handler {
    
    ArrayList<GameObject> object = new ArrayList<>();
    BattleCalculator bc1;
    BattleCalculator bc2;
    Inventory pouch = new Inventory(1,1,ID.Inventory);
    
    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObj = object.get(i);
            
            tempObj.tick();
            if(tempObj.currentVital < 0){
                removeObject(tempObj);
            }
            if(tempObj.getID() == ID.Player){
                detectCollisions((Player)tempObj);
            }
            if(tempObj.getID() == ID.Enemy){
                detectCollisions((EnemyGrumpsh)tempObj);
            }
        }
    }
    
    public void render(Graphics g){
        depthInsertionSort();
        for(int i = 0; i < object.size(); i++){
            GameObject tempObj = object.get(i);
            
            tempObj.render(g);
        }
    }
    public void depthInsertionSort(){
        for(int i = 0; i< object.size(); i++){
            GameObject temp = object.get(i);
   
            int j = i;
            while(j > 0 && temp.y + temp.z < object.get(j-1).y + object.get(j-1).z ){ 
            
                object.set(j, object.get(j-1));
                j--;                
            }
            object.set(j, temp);
        }
    }
    private void detectCollisions(GameObject movingObject){
        
        Rectangle movingBounds = movingObject.getBounds();
        Rectangle collisionSpace = new Rectangle(movingObject.x 
                    - Game.TILE_SIZE*3, movingObject.y - Game.TILE_SIZE*3,
                            Game.TILE_SIZE*6, Game.TILE_SIZE*6);
        for(int i = 0; i< object.size(); i++){
            GameObject temp = object.get(i);
            if(temp.getID() != movingObject.getID()){
            Rectangle tempBounds = temp.getBounds();
                if(collisionSpace.intersects(tempBounds)){
                    if(tempBounds.intersects(movingBounds)){
                        if(!temp.isWalkable ){
                            if(movingBounds.getMaxY() > tempBounds.getMaxY() ){ //obstruction above moving unit
                                
                                movingObject.setDownBlocked(false);
                                movingObject.setUpBlocked(true);   
                                
                            } else {
                                    movingObject.setUpBlocked(false);
                                    movingObject.setDownBlocked(true);    
                            }
                                                                                    
                            if(movingBounds.getMaxX() < tempBounds.getMaxX()){  //obstruction right of moving unit
                                                 
                                movingObject.setRightBlocked(true);
                                movingObject.setLeftBlocked(false);
                            } else { 
                                
                                movingObject.setRightBlocked(false);
                                movingObject.setLeftBlocked(true);
                                }   
                                
                            
                        } else {
                            movingObject.setUpBlocked(false);
                            movingObject.setRightBlocked(false);
                            movingObject.setLeftBlocked(false);
                            movingObject.setDownBlocked(false);
                        }
                        if(movingObject.id == ID.Player && temp.id == ID.Flora){
                            ((Player)movingObject).aquire(temp.id.toString(), 1, ((Flora)temp).getImage());
                            System.out.println("ID to string: " + temp.id.toString());
                            object.remove(i);
                        }
                         if(movingObject.id == ID.Enemy && temp.id == ID.Player){
                             bc1 = new BattleCalculator(movingObject, temp);
                             bc2 = new BattleCalculator(temp, movingObject);
                         }
                        
                    }
                    if(movingObject.id == ID.Enemy && temp.id == ID.Player){
                        ((EnemyGrumpsh)movingObject).setTarget(ID.Player);
                        ((EnemyGrumpsh)movingObject).setCharging(true);
                    }
                }
            }
        }   
    }
    
    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    public void setInventory(Inventory pouch){
        this.pouch = pouch;
    }
}
