package game.main;

public class Camera {
    private float x, y;
    
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public void tick(GameObject player){
        x += (-player.getX() + Game.WIDTH/2 - x) * 0.05;
        y += (-player.getY() + Game.HEIGHT/2 - y) * 0.05;
    }
    
    public float getX(){
        return x;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public float getY(){
        return y;
    }
    
    public void setY(float y){
        this.y = y;
    }

}
