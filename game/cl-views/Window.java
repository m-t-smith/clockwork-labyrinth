package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


public class Window { 
    
    private static final long serialVersionUID = 1L;

    private Handler handler;

    JPanel panel;

    JFrame frame;

    private Controller control;
    
  
    private Inventory pouch;
    
    public Window(int width, int height, String title, Game game, Handler handler){
             
        
        frame = new JFrame(title);
        panel = (JPanel)frame.getContentPane();
        
        this.handler = handler;
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));   
        frame.setMinimumSize(new Dimension(width, height));
        
       
       
        pouch = new Inventory(0,0,ID.Inventory);
        
        
        
        panel.add(game);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
        
        
        control = new Controller(handler);
        panel.add(control);

    }
    public Inventory getPouch(){
        return pouch;
    }
    
}
