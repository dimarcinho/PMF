
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
    
    private int x, y, vx, vy, size, life;
    private Color color;
    
    Particle(int x, int y, int vx, int vy, int size, int life, Color color){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
        this.life = life;
        this.color = color;
    }
    
    public boolean isActive(){        
        return (life > 0);
    }
    
    public void update(){
        x += vx;
        y += vy;
        life--;
    }
    
    public void draw(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(this.color);
        g2D.fillRect(x-(size/2), y-(size/2), size, size);
        //g2D.setFont(new Font("Arial", Font.ITALIC, 10+size*5));
        //g2D.drawString(""+rndAB(0,9),x-(size/2),y-(size/2));
    }
    
    private int rndAB(int a, int b){                
        int rndAB;
        Random rnd = new Random();
        rndAB = a+rnd.nextInt(b-a+1);
        return rndAB;
    
    }
}
