/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Marcio
 */
public class ParticleGenerator {
    
    public LinkedList<Particle> particles;
    public int x, y, n;
    
    public ParticleGenerator(int x, int y, int n){
        this.x = x;
        this.y = y;        
        this.n = n;
        
        particles = new LinkedList<Particle>();
        
        this.addParticles(x,y,n);
        
    }
    
    public void addParticles(int x, int y, int n){
        
        this.n =  n;
        
        Random rnd = new Random();
        
        for(int i = 0; i < n; i++){
            
            int vx = rnd.nextInt(16)-8;
            int vy = rnd.nextInt(16)-8;
            
            while(vx == 0 && vy == 0){
                vx = rnd.nextInt(16)-8;
                vy = rnd.nextInt(16)-8; 
            }
            
            int size = rnd.nextInt(3);
            int life = rnd.nextInt(10)+10;            
            
            particles.push(new Particle(x,y, vx, vy, size, life, Color.red));
                    
        }
    }
    
    public void update(){        
        
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update();
                if(!particles.get(i).isActive()){
                    particles.pop();
                }
        }
    }
    
    public void draw(Graphics g){
        for(Particle p : particles){
            p.draw(g);
        }
    }
}
