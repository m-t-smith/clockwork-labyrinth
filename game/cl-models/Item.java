package game.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Item extends JButton{
    String name;
    int quantity;
    int order;
    BufferedImage img;
    ImageIcon icon;
    boolean inUse;
        
        
    public Item(String name, int quantity, BufferedImage img) {
        this.name = name;
        this.quantity = quantity;
        this.img = img;
        this.order = 0;
        inUse = false;
        icon = new ImageIcon(img);
        this.setIcon(icon);
        this.setText(name + ":" + quantity);
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inUse = true;
            }
        });
    }
    @Override
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public String getName(){
        return name;
    }
    public BufferedImage getImage(){
        return img;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setOrder(int inventorySize){
        this.order = inventorySize;
    }

}
