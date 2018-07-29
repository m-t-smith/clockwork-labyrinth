/*
 6/23/2017
Game attempt
 */
package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.Point;






public class Game extends Canvas implements Runnable{
    
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1280, HEIGHT = WIDTH / 16 * 9; 
    public static final int TILE_SIZE = 32;
    public static final int NUM_ROOMS = 5;
    public static final int MAX_ROOM_SIZE = 6;
    public static BufferedImage sprite_sheet;
    public static BufferedImage run_sheet;
    private Thread thread;
    private boolean running = false;
    private Camera cam = new Camera(0,0);
    protected TileManager tileMan;
    private LevelBuilder lvlBuilder;
    private Handler handler;
    Window window;
    
    
    public Game() {
        
       handler = new Handler();
       lvlBuilder = new LevelBuilder(NUM_ROOMS, MAX_ROOM_SIZE);
       
       window = new Window(WIDTH, HEIGHT, "ClockWork Labyrinth", this, handler);
       
       BufferedImageLoader loader = new BufferedImageLoader();
       
       sprite_sheet = loader.loadImage("/res/spritesheet4.png/");
       
       
       
       int[][] map = lvlBuilder.getTileMap();
   
       Room start = lvlBuilder.getStartingRoom();
       Room spawn = lvlBuilder.getRandomRoom(NUM_ROOMS);
       tileMan = new TileManager(handler, map, start);
      
       
       Player player = new Player((int)tileMan.placeUnit(start).getX(),
                        (int)tileMan.placeUnit(start).getY(), ID.Player,
               window.getPouch());
       handler.addObject(player);
       handler.setInventory(window.getPouch());
       EnemyGrumpsh grumpsh1 = new EnemyGrumpsh((int) tileMan.placeUnit(spawn).x,
               (int) tileMan.placeUnit(spawn).y, ID.Enemy, map, handler);
       handler.addObject(grumpsh1);
     double[][] objectMap = lvlBuilder.getObjMap();
     for(int i = 0; i< objectMap.length; i++){
         for(int j = 0; j < objectMap[0].length; j++){
             if(objectMap[i][j] < 3 && objectMap[i][j] > 0.14){
                Point isoP = tileMan.twoDtoIso(new Point(j*Game.TILE_SIZE, i*Game.TILE_SIZE));
                handler.addObject(new Flora(isoP.x, isoP.y, ID.Flora));
             }
         }
     }
    
       
       
    }
   
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
          //      System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick(){
        handler.tick();
        for(int i = 0; i < handler.object.size(); i++){
           GameObject tempObj = handler.object.get(i);
           if(tempObj.getID() == ID.Player){
               cam.tick(tempObj);
               tileMan.tick(tempObj);
           }
        }
        
    }
    
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g2d.translate(cam.getX(), cam.getY());
        handler.render(g);
        g2d.translate(-cam.getX(), -cam.getY());
        
        g.dispose();
        bs.show();
       }
    
            
	public static void main(String[] args) {
                
            new Game();
            
		


    }
    
}
