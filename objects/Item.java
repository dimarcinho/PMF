/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Marcio
 */
public class Item extends Animated {    
    
    private int tipo;
    private String[] path;
    
    private int w, h;
    
    public Item(int x, int y, int tipo){
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        
        vx = 0;
        vy = 0;
        
        path = new String[10];
        
        this.init();
                
        this.setSS(path[this.tipo]);

    }
    
    @Override
    public void init(){
        
        //Definir neste vetor todos os caminhos de todos os itens do jogo
        
        path[0] = "/res/img/barril 32x32.png";
        path[1] = "/res/img/green_flag.png";
        
        AdjustItemAnimation(tipo);
    }
    
    @Override
    public void update(){
        this.Animation();
    }
    
    @Override
    public void draw(Graphics g){
        g.drawImage(this.getAnimatedImage(), this.x, this.y, null);
    }
    
    public void setSS(String path){
        this.ss = new SpriteSheet(this.i.load(path));
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;        
    }
    
    private void AdjustItemAnimation(int tipo){
        switch(tipo){
            default:
                System.out.println("Erro em AdjustItemAnimation() da classe Item");
                break;
            case 0:
                //barril está sem animação
                w = 32;
                h = 32;
                break;
            case 1:
                setFrames(0, 5);
                w = 64;
                h = 96;
                frameSpeed = 1;
                break;
        }
    }
    
    @Override
    public Rectangle getBounds(){
        Rectangle r = new Rectangle(this.x, this.y, w, h);
        return r;
    }
          
    @Override
    public void Animation(){
        
        frameSS = ss.crop2(frameNumber*w, 0, w, h);
        
        if(counterSS % frameSpeed == 0){
            if(frameNumber < endFrame){
                frameNumber++;
            } else {
                frameNumber = startFrame;
            }   
        }
        
        if(counterSS > 20*frameSpeed){
            counterSS = 0;
        } else {
            counterSS++;
        }
        
        if(vx == 0 && vy == 0)
            counterSS--;
    
    }
}
