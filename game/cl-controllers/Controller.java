package game.main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Controller extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Map<Direction, Boolean> directionMap = new HashMap<Direction, Boolean>();
    private Map<Direction, Point> moveMap = new HashMap<Direction, Point>();
    private Handler handler;
    private static final int TIMER_DELAY =  10;
    private boolean showInventory;
    
    
    enum Direction{
        
        UP(KeyEvent.VK_UP, 0, -1), DOWN(KeyEvent.VK_DOWN, 0, 1),
        LEFT(KeyEvent.VK_LEFT, -1, 0), RIGHT(KeyEvent.VK_RIGHT, 1, 0);
        private int keyCode;
        private int xDirection;
        private int yDirection;
        

        private Direction(int keyCode, int xDirection, int yDirection) {
            this.keyCode = keyCode;
            this.xDirection = xDirection;
            this.yDirection = yDirection;
        }
        
        public int getKeyCode() {
            return keyCode;
        }

        public int getXDirection() {
            return xDirection;
        }

        public int getYDirection() {
            return yDirection;
        }
    }
    
    public Controller(Handler handler){
        this.handler = handler;
    
        showInventory = false;
        
        for (Direction direction : Direction.values()) {
            directionMap.put(direction, false);
        }
        setKeyBindings(this);
        Timer timer = new Timer(TIMER_DELAY, new TimerListener());
        timer.start();
        
        
    }
    
    
    private void setKeyBindings(Controller control) {
        InputMap inMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actMap = getActionMap();
        
        inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Inventory pressed");
        actMap.put("Inventory pressed", new AbstractAction() {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
               if(!showInventory){
                   showInventory = true;
                   handler.pouch.show(control, 0, 0);
                   handler.pouch.requestFocus();
               } else{
                   
                    showInventory = false;     
                    handler.pouch.setVisible(showInventory);
                    }
            }
            
        });
        
        for (final Direction direction : Direction.values()) {
            KeyStroke pressed = KeyStroke.getKeyStroke(direction.getKeyCode(), 0, false);
            KeyStroke released = KeyStroke.getKeyStroke(direction.getKeyCode(), 0, true);
            inMap.put(pressed, direction.toString() + "pressed");
            inMap.put(released, direction.toString() + "released");
            actMap.put(direction.toString() + "pressed", new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    directionMap.put(direction, true);
                    moveMap.put(direction, new Point(direction.getXDirection(), 
                                                    direction.getYDirection()));
          
                }
            });
            actMap.put(direction.toString() + "released", new AbstractAction() {

                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    directionMap.put(direction, false);
                    moveMap.remove(direction);
                    
                }
            });
        }
        
    }
    public boolean getShowInventory(){
        return showInventory;
    }
    public void setShowInventory(boolean show){
        showInventory = show;
    }
    private class TimerListener implements ActionListener {

       
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int x = 0;
            int y = 0;
            
            for(Point point : moveMap.values()){
                x += point.x;
                y += point.y;
            }
            
            for(int i = 0; i < handler.object.size(); i++){
                GameObject tempObj = handler.object.get(i);
                try{
                    if(tempObj.getID() == ID.Player){
                        tempObj.setDirectionX(x);
                        tempObj.setDirectionY(y);
                    }
                }catch(Exception ex){
                     System.out.println(ex.toString());  
                    }

            }
      
        }
    }
}
