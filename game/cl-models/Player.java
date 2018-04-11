
package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;





public class Player extends GameObject {
    
    
    private final BufferedImage[] walk_left = new BufferedImage[3];
    private final BufferedImage[] walk_right =  new BufferedImage[3];
    private final BufferedImage[] standing = new BufferedImage[3];
    private final BufferedImage[] walk_down = new BufferedImage[3];
    private final BufferedImage[] walk_up = new BufferedImage[2];
    private final Animation standingAn;
    private final Animation walking_left;
    private final Animation walking_right;
    private final Animation walking_down;
    private final Animation walking_up;
    private Animation animation;
    private Inventory myInventory;
    private static final int TIMER_DELAY = 10*1000;
    
    
    public Player(int x, int y, ID id, Inventory pouch){
        super(x, y, id);
        
        z = 45;
        celerity = 2; //moveSpeed, initiative in battle
        vitality = 100;
        currentVital = vitality;
        puissance = 1;
        animus = 3;
        width = Game.TILE_SIZE/2;
        height = Game.TILE_SIZE;
        rightBlocked = false;
        upBlocked = false;
        myInventory = pouch;

        isWalkable = true;
        
        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
      
        standing[0] = ss.grabImage(1, 4);
        standing[1] = ss.grabImage(2, 1);
        standing[2] = ss.grabImage(2, 2);
        standingAn = new Animation(standing, 10);
        walk_left[0] = ss.grabImage(1, 1);
        walk_left[1] = ss.grabImage(1, 2);
        walk_left[2] = ss.grabImage(1, 3);
        walking_left = new Animation(walk_left, 10);
        walk_right[0] = ss.grabImage(2, 3);
        walk_right[1] = ss.grabImage(2, 4);
        walk_right[2] = ss.grabImage(3, 1);
        walking_right = new Animation(walk_right, 10);
        walk_down[0] = ss.grabImage(3, 4);
        walk_down[1] = ss.grabImage(4, 1);
        walk_down[2] = ss.grabImage(4, 2);
        walking_down = new Animation(walk_down, 10);
        walk_up[0] = ss.grabImage(4, 3);
        walk_up[1] = ss.grabImage(4, 4);
        walking_up = new Animation(walk_up, 13);
        
    }
    
    @Override
    public void tick(){
        
        animation.update();
        if(rightBlocked){
            directionX = -1;
            rightBlocked = false;
        }
        if(leftBlocked){
            directionX = 1;
            leftBlocked = false;
        }
        if(upBlocked){
            directionY = 1;
            leftBlocked = false;
        }
        if(downBlocked){
            directionY = -1;
            downBlocked = false;
        }
        if(animus > 0){
            if(myInventory.getItemsInUse().contains("Flora")){
                myInventory.setItemUsed("Flora");
                bulletTime();
            } 
        }
  
        x += (directionX * celerity *1.6);
   
        y += directionY * celerity ;

    }
    
    @Override
    public void render(Graphics g){
        if(directionX < 0 ){
           animation = walking_left;
        } else if(directionX > 0){
           animation = walking_right;
        } else if(directionY > 0){
            animation = walking_down;
        }else if (directionY < 0) {
            animation = walking_up;
        } else {
            animation = standingAn;
        } 
            animation.start();
            g.drawImage(animation.getSprite(), x, y, null);
            
            showVit(g);
    }
    
    @Override
    public Rectangle getBounds(){
        return new Rectangle(x + 24, y + Game.TILE_SIZE/2, width,
                height - height/5);
    }
    
    public void aquire(String name, int quantity, BufferedImage img){
        myInventory.addToMenuAndMap(new Item(name, quantity, img));
    }

    private void bulletTime() {
        celerity += 1;
        animus--;
        Timer timer = new Timer(TIMER_DELAY, new TimerListener(this));
        timer.setRepeats(false);
        
        timer.start();
    }
    
    private class TimerListener implements ActionListener {
        Player player;
        public TimerListener(Player player){
            this.player = player;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            player.celerity -= 1;
        }
        
    }
    
  
}
