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
        
        particles = new LinkedList<>();
                
    }
    
    public void addParticles(int x, int y, int vx, int vy,
                            int size, int life, Color c){
                
        particles.push(new Particle(x, y, vx, vy, size, life, c));
        
    }
    
    public void addBasicParticles(int x, int y, int n){
        
        this.n =  n;
        
        Random rnd = new Random();
        
        for(int i = 0; i < n; i++){
            
            int vx = rnd.nextInt(16)-8;
            int vy = rnd.nextInt(16)-8;
            
            while(vx == 0 || vy == 0){
                vx = rnd.nextInt(16)-8;
                vy = rnd.nextInt(16)-8; 
            }
            
            int size = rnd.nextInt(3);
            int life = rnd.nextInt(10)+10;            
            
            particles.push(new Particle(x, y, vx, vy, size, life, Color.red));
            
            //testes
            //Color c = new Color(rndAB(1,254), rndAB(1,254), rndAB(1,254), rndAB(1,254));            
            //Color c = new Color(0.7f,0.2f,0.3f, 0.1f);
            //particles.push(new Particle(x, y, vx, vy, size, life*100, c));
                    
        }
        
    }
    
    private int rndAB(int a, int b){                
        int rndAB;
        Random rnd = new Random();
        rndAB = a+rnd.nextInt(b-a+1);
        return rndAB;
    }
    
    public void update(){        
        
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update();
                if(!particles.get(i).isActive()){
                    particles.pop();
                }
        }
    }
    
    public void removeAll(){
        particles.clear();
    }
    
    public void draw(Graphics g){
        for(Particle p : particles){
            p.draw(g);
        }
    }
}
