/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Rectangle;

/**
 *
 * @author Marcio
 */
public class Zone {
    
    private Rectangle area;
    
    public Zone(int x, int y, int width, int height){
        this.area = new Rectangle(x, y, width, height);
    }
    
    public Rectangle getBounds(){
        return area;
    }
    
    public Rectangle getCenter(){
        //quadrado de 4x4 pixels em torno do centro;
        Rectangle r = new Rectangle();
        r.x = area.x + (int)(area.width/2) - 2;
        r.y = area.y + (int)(area.height/2) - 2;
        r.width = 4;
        r.height = 4;
        return r;
    }
}
