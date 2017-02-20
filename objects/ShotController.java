
package objects;

import java.awt.Graphics;
import java.util.LinkedList;


public class ShotController {
    
    public LinkedList<Shot> shots;
    
    public ShotController(){
        
        shots = new LinkedList<>();
        
    }
    
    public void addShot(Shot e){
        shots.add(e);        
    }
    
    public void removeShot(Shot e){
        shots.remove(e);
    }
    
    public void removeAll(){
        shots.clear();
    }
    
    public void update(){
        for(Shot e : shots){
            e.update();
        }
    }
    
    public void draw(Graphics g){
        for(Shot e : shots){
            e.draw(g);
        }
    }
    
}
