/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;

/**
 *
 * @author Marcio
 */
public class Item extends Animated {    
    
    private int tipo;
    private String[] path;
    
    public Item(int x, int y, int tipo){
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        
        vx = 0;
        vy = 0;
        
        path = new String[10];
        
        this.init();
                
        this.setSS(path[this.tipo]);
        //System.out.println(""+path[this.tipo]);
    }
    
    @Override
    public void init(){
        
        //Definir neste vetor todos os caminhos de todos os itens do jogo
        
        path[0] = "/res/barril 32x32.png";
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
}
