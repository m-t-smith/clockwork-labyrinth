package game.main;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

 
public class Inventory extends JPopupMenu {
    
    HashMap<String, Item> items = new HashMap<>();
    int moveSpeed = 2;
    int width;
    int height;
    boolean isVisible;
    String itemsInUse;
    MouseAdapter mouseAdapter;
    
    
    public Inventory(int x, int y, ID id){
        mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){
            for(Item item : items.values()){
                updateOnUse(item);
            }
        }};
        
        isVisible = true;
        itemsInUse = "";
//        z = 900;
        this.setFocusable(isVisible);
        this.add(new JMenuItem("You stare into the void. The void stares into you."));
        
    }
    public void setItemUsed(String item){
        String blank = "";
        itemsInUse = itemsInUse.replaceFirst(item, blank);
    }
 

  public void addToMenuAndMap(Item item){
     String name = item.getName();
     Item itemInMap = items.get(name);
     int quantity = item.getQuantity();
     if(this.getSubElements().length == 1){
         if(this.getComponent(0).getName() == null){
             this.remove(0);
         }
     }
     if(itemInMap==null) { // add a new group, if none exist yet  
       item.setOrder(this.getSubElements().length);
       item.addMouseListener(mouseAdapter); //add mouse listener to button for updating
       this.add(item); //add button to display
       items.put(name, item); //add item to hashmap
     } else{
        itemInMap.quantity += quantity; //update item count in map
        itemInMap.setText(name + ": " + itemInMap.quantity); //update item count in display
     }  
     
  }
  public void updateOnUse(Item item){
      if(item.inUse){
          String name = item.getName();
          if("Flora".equals(name)){
              itemsInUse = itemsInUse.concat(name);
          }
          remove(name);
          item.inUse = false;
      }
  }
  public String getItemsInUse(){
      return itemsInUse;
  }


  public void remove(String name){
     Item itemInMap = items.get(name);
     if(itemInMap==null) return;
     itemInMap.quantity -= 1;
     
     if(itemInMap.quantity > 0){
        itemInMap.setText(name + ": " + itemInMap.quantity); //update item
     } else {
     // remove an empty group
        this.remove(itemInMap.order);
        items.remove(name);
    
        if(this.getSubElements().length == 0){
            this.add(new JMenuItem("Inventory is Empty"));
        }
    }
     
  }
  public void setVisibile(boolean v){
      this.isVisible = v;
  }

//    @Override
//    public void tick() {
//        x += (directionX * moveSpeed *1.6);
//   
//        y += directionY * moveSpeed;
//    }
//
//    @Override
//    public void render(Graphics g) {
//        if(this.isVisible()){
//            
//            g.setColor(Color.DARK_GRAY);
//            g.fillRect(x, y, width, height);
//            items.forEach((k, v) -> g.drawImage(
//                    v.img, x + (v.order * Game.TILE_SIZE), y, null));
//            g.setColor(Color.WHITE);
//            items.forEach((k, v) -> g.drawString(v.quantity + "",
//                    x + (v.order + 1) * Game.TILE_SIZE, y + Game.TILE_SIZE));
//            
//            
//            
//        }
//    }

}
