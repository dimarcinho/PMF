
package objects;

import java.awt.Graphics;

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

        col0 = (p.x-width/2)/32;
        row0 = (p.y-height/2)/32;
        colf = (p.x+width/2)/32;
        rowf = (p.y+height/2)/32;
        
        col0 = (p.x-100)/32;
        row0 = (p.y-100)/32;
        colf = (p.x+100)/32;
        rowf = (p.y+100)/32;

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
        //map.draw(g, col0, colf, row0, rowf);
        
    }
    
}
