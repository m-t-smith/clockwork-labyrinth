package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public class Flora extends GameObject {
    
    private BufferedImage[] sway = new BufferedImage[4];
    private BufferedImage[] bloom = new BufferedImage[6];
    private BufferedImage[] close = new BufferedImage[5];
    private Animation swaying;
    private Animation blooming;
    private Animation closing;
    private Animation animation;
 
    
    public Flora(int x, int y, ID id){
        super(x,y,id);
        
         z = 20;
         width = Game.TILE_SIZE/4;
         height = Game.TILE_SIZE/4;
         SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
         
         sway[0] = ss.grabSmallImage(6, 9, 32);
         sway[1] = ss.grabSmallImage(6, 10, 32);
         sway[2] = ss.grabSmallImage(6, 11, 32);
         sway[3] = ss.grabSmallImage(6, 12, 32);
         swaying = new Animation(sway, 20);
         bloom[0] = ss.grabSmallImage(5, 9, 32);
         bloom[1] = ss.grabSmallImage(5, 10, 32);
         bloom[2] = ss.grabSmallImage(5, 11, 32);
         bloom[3] = ss.grabSmallImage(5, 12, 32);
         bloom[4] = ss.grabSmallImage(5, 13, 32);
         bloom[5] = ss.grabSmallImage(5, 14, 32);
         blooming = new Animation(bloom, 20);
         close[0] = ss.grabSmallImage(5, 15, 32);
         close[1] = ss.grabSmallImage(5, 16, 32);
         close[2] = ss.grabSmallImage(6, 13, 32);
         close[3] = ss.grabSmallImage(6, 14, 32);
         close[4] = ss.grabSmallImage(5, 9, 32);
         closing = new Animation(close, 20);
         
         isVisible = false;
         isWalkable = true;
         
         animation = blooming;
         timer();
       //  born = System.nanoTime();
         
         
    }
    public void timer(){
        Timer timer = new Timer(true);

        timer.schedule(new TimerTask() {
             @Override
             public void run() {
              if(animation == swaying){
                  animation = closing;
                  animation.reset();
              }
              
            }
            }, 0, 10*1000); //change these numbers to make it work smoothly i think also try daemon
    }
    public BufferedImage getImage(){
        return bloom[5];        
    }

    @Override
    public void tick() {
       animation.update();
       if (animation == closing && animation.getCurrentFrame() == 4){
           animation = blooming;
           animation.reset();
       }
        
       if(animation == blooming && animation.getCurrentFrame() == 5){
           animation = swaying;
          
       }
    }  

    @Override
    public void render(Graphics g) {
        
        if(this.isVisible){
        animation.start();
            g.drawImage(animation.getSprite(), x, y, null);
          //  g.fillRect(x, y, width*2, height*2);
        }
    }

}
