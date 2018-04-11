package game.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class RTile extends GameObject {

  private BufferedImage RStile_image;
  
    public RTile(int x, int y, ID id){
        super(x, y, id);
        
        
        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        
        RStile_image = ss.grabImage(3, 3);
        
        isWalkable = false;
        isVisible = false;
        width = Game.TILE_SIZE;
        height = Game.TILE_SIZE/2;
        z = 40;
    }
    
  @Override
    public Rectangle getBounds(){
        return new Rectangle(x + width/2, y + height/2, width + 4, height);
    }
    
    
    @Override
    public void tick(){
        
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(RStile_image, (int) x, (int) y, null);
    //   g.fillRect(x + width/2, y + height/2, width + 4, height);
     
        
    }
}