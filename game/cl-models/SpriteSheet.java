package game.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    
    private static BufferedImage sprite;
    private static final int TILE_SIZE = 64;
    
    public SpriteSheet(BufferedImage ss){
        this.sprite = ss;
    }
    
    public BufferedImage grabImage(int col, int row){
        BufferedImage img = sprite.getSubimage((row * TILE_SIZE) - TILE_SIZE, (col * TILE_SIZE) - TILE_SIZE,
                TILE_SIZE, TILE_SIZE);
        return img;
    }
    public BufferedImage grabSmallImage(int col, int row, int size){
        BufferedImage img = sprite.getSubimage((row * size) - size,
                        (col * size) - size, size, size);
        return img;
    }
}
