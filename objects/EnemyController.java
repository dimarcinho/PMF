/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Marcio
 */
public class EnemyController {
    
    public LinkedList<Enemy> enemies;
    
    public EnemyController(){
        enemies = new LinkedList<Enemy>();
    }
    
    public Enemy getEnemy(){        
        return enemies.element();
    }
    
    public void addEnemy(Enemy e){
        enemies.add(e);        
    }
    
    public void removeEnemy(Enemy e){
        enemies.remove(e);
    }
    
    public void update(){
        for(Enemy e : enemies){
            e.update();
        }
    }
    
    public void draw(Graphics g){
        for(Enemy e : enemies){
            e.draw(g);
        }
    }
}
