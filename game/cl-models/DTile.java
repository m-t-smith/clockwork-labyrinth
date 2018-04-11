
package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DTile extends GameObject {

  private BufferedImage DStile_image;
  
    
    public DTile(int x, int y, ID id){
        super(x, y, id);
        
        
        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        
        DStile_image = ss.grabImage(3, 2);
        
        isWalkable = true;
        isVisible = false;
        width = Game.TILE_SIZE;
        height = Game.TILE_SIZE;
        z = 0;
    }
    
    
    @Override
    public void tick(){
        
    }
    public boolean getIsWalkable(){
        return isWalkable;
    }
    
    
    
    @Override
    public void render(Graphics g){
        
        if(isVisible){
         g.drawImage(DStile_image, (int) x, (int) y, null);   
        
        }
    }
}

