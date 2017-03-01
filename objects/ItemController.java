
package objects;

import java.awt.Graphics;
import java.util.LinkedList;

public class ItemController {
    
    public LinkedList<Item> items;
    
    public ItemController(){
        items = new LinkedList<Item>();
    }
    
    public Item getItem(){        
        return items.element();
    }
    
    public void addItem(Item i){
        items.add(i);        
    }
    
    public void removeItem(Item i){
        items.remove(i);
    }
    
    public void update(){
        for(Item i : items){
            i.update();
        }
    }
    
    public void draw(Graphics g){
        for(Item i : items){
            i.draw(g);
        }
    }
}
