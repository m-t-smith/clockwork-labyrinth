package game.main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;




public class EnemyGrumpsh extends GameObject {
    
    private BufferedImage[] walk_left = new BufferedImage[2];
    private BufferedImage[] walk_right =  new BufferedImage[2];
    private BufferedImage[] charge_left = new BufferedImage[2];
    private BufferedImage[] charge_right = new BufferedImage[2];
    private Animation walking_left;
    private Animation walking_right;
    private Animation charging_left;
    private Animation charging_right;
    private Animation animation;
    private int count;
    private int[][] map;
    private PathAI pather;
    private Handler handler;
    private Point wayPointInMap;
    private Point placeInMap;
    private Point destination;
    private Point isoWayPoint;
    private ID target;
    private boolean charging;
  

    public EnemyGrumpsh(int x, int y, ID id, int[][] map, Handler handler){
        super(x,y,id);
        
        z = 40;
        celerity = 1;
        puissance = 1;
        vitality = 50;
        currentVital = vitality;
        width = Game.TILE_SIZE;
        height = Game.TILE_SIZE;
        rightBlocked = false;
        upBlocked = false;
        downBlocked = false;
        leftBlocked = false;
        isVisible = true;
        count = 0;
        this.map = map;
        this.handler = handler;
        target = ID.Flora;
        charging = false;
         
        
        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        
        walk_left[0] = ss.grabImage(1, 5);
        walk_left[1] = ss.grabImage(1, 6);
        walking_left = new Animation(walk_left, 12);
        walk_right[0] = ss.grabImage(1, 7);
        walk_right[1] = ss.grabImage(1, 8);
        walking_right = new Animation(walk_right, 12);
        charge_right[0] = ss.grabImage(2, 7);
        charge_right[1] = ss.grabImage(2, 8);
        charging_right = new Animation(charge_right, 10);
        charge_left[0] = ss.grabImage(2, 5);
        charge_left[1] = ss.grabImage(2, 6);
        charging_left = new Animation(charge_left, 10);
        
        makePath(target);
        changeVector();
    }
    
    @Override
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
    
    private void makePath(ID target){
        destination = new Point(0,0);
        
        for(int i = 0; i< handler.object.size(); i++){
            GameObject tempObj = handler.object.get(i);
            if(tempObj.getID() == target && destination.equals(new Point(0,0))){
                destination = new Point(tempObj.x, tempObj.y);
            }
        }
        updatePosition();
        destination = isoTo2D(destination);
        destination = new Point((int)Math.floor(destination.x/Game.TILE_SIZE),
                (int) Math.floor(destination.y/Game.TILE_SIZE));
        pather = new PathAI(map, placeInMap, destination);
        wayPointInMap = pather.getnextPoint();
      //  System.out.println(pather.getPath().toString());
        
    }
    
    public void updatePosition(){
        Point place2D = isoTo2D(new Point(x, y));
        placeInMap = new Point((int) Math.floor(place2D.x/Game.TILE_SIZE),
                        (int) Math.floor(place2D.y/Game.TILE_SIZE));
        
    }
    public void setCharging(boolean charge){
        this.charging = charge;
    }
    
    private void changeVector(){
          updatePosition();     
                
        if(placeInMap.equals(wayPointInMap) || isoWayPoint == null){
            wayPointInMap = pather.getnextPoint();
            if(wayPointInMap != null){
       
                Point nextScreenPoint = new Point(wayPointInMap.x*Game.TILE_SIZE,
                                wayPointInMap.y*Game.TILE_SIZE);
                isoWayPoint = twoDtoIso(nextScreenPoint);
            }
        }

                if(isoWayPoint != null){
                    if( x < isoWayPoint.x){
                        directionX = 1;
                    } else if(x > isoWayPoint.x) {
                        directionX = -1;
                    } else {
                        directionX = 0;
                    }
                    if(y < isoWayPoint.y){
                        directionY = 1;
                    } else if (y > isoWayPoint.y) {
                        directionY = -1;
                    } else {
                        directionY = 0;
                    }
                }
                
                
    }
    public void setTarget(ID target){
        this.target = target;
    }
    
    public Point twoDtoIso(Point pt){
        Point tempPt = new Point(0, 0); 
         tempPt.x =  (int) (pt.getX() - pt.getY());
         tempPt.y = (int) ((pt.getX() + pt.getY()) / 2);
         
        return tempPt;
    }
    
    public Point isoTo2D(Point pt){
        Point tempPt = new Point(0,0);
        tempPt.x = (int) (2 * pt.getY() + pt.getX()) / 2;
        tempPt.y = (int) (2 * pt.getY() - pt.getX()) / 2;
        return(tempPt);
    }
    
    @Override
    public void tick(){
        
        animation.update();
        int trans = 1;
        
        if(rightBlocked && upBlocked){
             x -= 1;
            this.isoWayPoint.translate(-trans, trans);
                rightBlocked = false;
                
//                System.out.println("RB");
                y += 1;
                changeVector();
                upBlocked = false;
                
//                System.out.println("UB");
//                System.out.println(this.isoWayPoint.toString());
        }else if(rightBlocked && downBlocked){
            x -= 1;
            this.isoWayPoint.translate(-trans, -trans);
                rightBlocked = false;
                
//                System.out.println("RB");
                y -= 1;
                
                downBlocked = false;
                changeVector();
//                System.out.println("DB");
//                System.out.println(this.isoWayPoint.toString());
        }else if(leftBlocked && upBlocked){
             x += 1;
                this.isoWayPoint.translate(trans, trans);
                leftBlocked = false;
              
//                System.out.println("LB");
                y += 1;
                
                upBlocked = false;
                changeVector();
//                System.out.println("UB");
//                System.out.println(this.isoWayPoint.toString());
        }else if(leftBlocked && downBlocked){
             x += 1;
                this.isoWayPoint.translate(trans, -trans);
                leftBlocked = false;
               
//                System.out.println("LB");
                y -= 1;
                
                downBlocked = false;
                changeVector();
//                System.out.println("DB");
//                System.out.println(this.isoWayPoint.toString());
                
        } else  {
            if(rightBlocked){
                x -= 1;
                this.isoWayPoint.translate(-trans, 0);
                rightBlocked = false;
                changeVector();
//                System.out.println("RB");
//                System.out.println(this.isoWayPoint.toString());
            } else if(leftBlocked){
                x += 1;
                this.isoWayPoint.translate(trans, 0);
                leftBlocked = false;
                changeVector();
//                System.out.println("LB");
//                System.out.println(this.isoWayPoint.toString());
            } else {
                if(charging){
                    celerity +=1;
                    x += (directionX * celerity *1.7);
                count++;
                    celerity -=1;
                } else{
                x += (directionX * celerity *1.7);
                count++;
                }
            } 
            if(upBlocked){
                y += 1;
                this.isoWayPoint.translate(0, trans);
                upBlocked = false;
                changeVector();
//                System.out.println("UB");
//                System.out.println(this.isoWayPoint.toString());
            } else if(downBlocked){
                y -= 1;
                this.isoWayPoint.translate(0, -trans);
                downBlocked = false;
                changeVector();
//                System.out.println("DB");
//                System.out.println(this.isoWayPoint.toString());
            } else {
                y += directionY * celerity ;
                count++;
            }  
        
        if (count > 80){
            makePath(target);
            changeVector();
            count = 0;
        }
        }
    }
    
    @Override
    public void render(Graphics g){
        if(directionX < 0 ){
            if(!charging){
                animation = walking_left;
            } else { 
                animation = charging_left;
            }
        } else if(directionX > 0){
            if(!charging){
                animation = walking_right;
            } else { 
                animation = charging_right;
            }
        } else animation = walking_left;
        animation.start();
        g.drawImage(animation.getSprite(), x, y, null);
           // g.fillRect(x, y, width, height);
           // x + width/2, y + height/2, width, height old val
           
        showVit(g);
            
    }
}
