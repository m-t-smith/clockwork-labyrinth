/*
Game Object 
player, enemy, npc etc
 */
package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    
    protected int x, y;
    protected double z;
    protected int celerity;
    protected int vitality;
    protected int puissance;
    protected int animus;
    protected ID id;
    protected int directionX, directionY;
    protected int width;
    protected int height;
    protected boolean isVisible;
    protected boolean rightBlocked;
    protected boolean leftBlocked;
    protected boolean upBlocked;
    protected boolean downBlocked;
    protected boolean isWalkable;
    protected float currentVital;
    
    
    
   
    
    
    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public void setID(ID id){
        this.id = id;
    }
    public ID getID(){
        return id;
    }
    public void setDirectionX(int directionX){
        this.directionX = directionX;
    }
    public void setDirectionY(int directionY){
        this.directionY = directionY;
    }
    public int getDirectionX(){
        return directionX;
    }
    public int getDirectionY(){
        return directionY;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width*2, height*2);
    }
    public boolean isVisible(){
        return isVisible;
    }
    public void setIsVIsible(boolean isVisible){
        this.isVisible = isVisible;
    }
    public void setRightBlocked(boolean blocked){
        this.rightBlocked = blocked;
    }
    public void setLeftBlocked(boolean blocked){
        this.leftBlocked = blocked;
    }
    public void setUpBlocked(boolean blocked){
        this.upBlocked = blocked;
    }
    public void setDownBlocked(boolean blocked){
        this.downBlocked = blocked;
    }
    public boolean getRightBlocked(){
        return rightBlocked;
    }
    public boolean getLeftBlocked(){
        return leftBlocked;
    }
    public boolean getUpBlocked(){
        return upBlocked;
    }
    public boolean getDownBlocked(){
        return downBlocked;
    }
    public void setWalkable(boolean isWalkable){
        this.isWalkable = isWalkable;
    }
    public boolean getIsWalkable(){
        return isWalkable;
    }
    public void changeCelerity(int c){
        this.celerity = c;
    }
    public int getCelerity(){
        return celerity;
    }
    public int getVitality(){
        return vitality;
    }
    public float getCurrentVital(){
        return currentVital;
    }
    public int getPuissance(){
        return puissance;
    }
    public void changePuissance(int p){
        this.puissance += p;
    }
    public void changeCurrentVital(int a){
        this.currentVital += a;
    }
    public void showVit(Graphics g){
        if(currentVital < vitality){
           g.setColor(Color.BLACK);
           g.drawRect(x+5, y - Game.TILE_SIZE/2,
                   vitality/2, 5);
           g.setColor(Color.GREEN);
           g.fillRect(x+5, y - Game.TILE_SIZE/2,
                   (int)currentVital/2, 5 );
           }
    }
    
    
    
}
