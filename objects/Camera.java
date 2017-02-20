
package objects;

import java.awt.Color;
import java.awt.Graphics;
import pmf.PMF;

public class Camera {
    
    private int x, y, maxX, maxY;    
    private int width, height;
    public int offsetX, offsetY, lastoffsetX, lastoffsetY;
    
    private Player p;
    public MapTile map;
    public int[] logic;
    
    public int row0, rowf, col0, colf;
    
    public Camera(Player p, MapTile map){
        
        this.p = p;
        this.map = map;
        
        this.logic = map.logicGrid;
        
        //tamanho do enquadramento da câmera
        this.width = 1024;
        this.height = 768;
        
        //usando na verdade 100% da área como câmera do jogo:
        //this.width = PMF.WIDTH;
        //this.height = PMF.HEIGHT;
        
        //limites
        maxX = map.getCol()*map.getTsize() - width;
        maxY = map.getRow()*map.getTsize() - height;
        
        //posicionando a câmera em relação ao player
        this.x = -p.x + width/2;
        this.y = -p.y + height/2;     
        
    }
    
    public void update(Player p){

        col0 = 0;
        row0 = 0;
        colf = map.col;
        rowf = map.row;                

        offsetX = -p.x+this.width/2;
        offsetY = -p.y+this.height/2+100;
        
        this.x = p.x - width/2;
        this.y = p.y - height/2;
        
        if(p.x < this.width/2){
            offsetX = 0;
        } else if (p.x > maxX + width/2){
            offsetX = -maxX;// - width/2;
        }
        
        if(p.y < this.height/2){
            offsetY = 100;
        } else if (p.y > maxY + height/2 + 100){
            offsetY = -maxY;// + height/2+100;
        }
        
    }
    
    public void draw(Graphics g){

        g.translate(offsetX, offsetY);       
        map.draw(g);
        
    }
    
}
