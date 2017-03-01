
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class MapTile {
    
    public int row, col;
    public int tsize;
    public int[] tile;
    public int[] logicGrid;
    
    public ImageLoader i = new ImageLoader();
    public BufferedImage image;
    
    String nuvem_path = "/res/img/nuvem.png";
    String tiles = "/res/img/tiles.png";
    
    private String mapfile;
    
    private SpriteSheet ss;
    
    public MapTile(int tsize, String mapfile){
        this.tsize = tsize;
        this.mapfile = mapfile;
        
        this.updateRowCol(mapfile);
        
        this.tile = new int[row*col];
        this.logicGrid = new int[row*col];
        
        
                
        ss = new SpriteSheet(i.load(tiles));
        
        //popula o tiles em forma de teste
        //this.populaMapa();        
        
        this.loadImageLevel(mapfile);
        this.createLogic();
        
    }
    
    private void createLogic(){
        
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < this.col; j++){
                
                this.logicGrid[i*col+j] = 0; //1 para collision true
                
                if (this.tile[i*col+j] == 1 || this.tile[i*col+j] == 2
                        || this.tile[i*col+j] == 4){                    
                    this.logicGrid[i*col+j] = 1;
                }
                
            }
        }   
    }
    
    public void updateRowCol(String path){
        
        image = this.i.load(path);
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        this.col = width;
        this.row = height;
        
        //System.out.println("w, h:"+width+", "+height);
        
    }
    
    public void loadImageLevel(String path){
        
        image = i.load(path);
        
        int width = image.getWidth();
        int height = image.getHeight();        
        
        this.col = width;
        this.row = height;
             
        
        for(int i = 0; i < row; i++){
            
            for(int j = 0; j < col; j++){
                
                int pixel = image.getRGB(j, i);                
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                //System.out.println("red, green, blue: "+red+","+green+","+blue);
                
                //Color c = new Color(pixel, true);
                //int cores = c.getRGB();                
                if(red == 255 && green == 255 && blue == 255)
                    this.tile[i*col+j] = 0;
                
                if(red == 0 && green == 0 && blue == 0)
                    this.tile[i*col+j] = 1;
                
                if(red == 255 && green == 0 && blue == 0)
                    this.tile[i*col+j] = 2;
                
                if(red == 0 && green == 255 && blue == 0)
                    this.tile[i*col+j] = 3;
                
                if(red == 0 && green == 0 && blue == 255)
                    this.tile[i*col+j] = 4;
                
                //para criar inimigos
                if(red == 255 && green == 0 && blue == 255)
                    this.tile[i*col+j] = 5;
            }
        }        
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getTsize() {
        return tsize;
    }
    
    //i - linha, j - coluna
    public int getTile(int i, int j){
        return this.tile[i*col+j];
    }
    
    public int getLogic(int i, int j){
        return this.logicGrid[i*col+j];
    }
    //i - linha, j - coluna
    public Rectangle getTileBounds(int i, int j){
        Rectangle r = new Rectangle(j*tsize, i*tsize, 32, 32);
        return r;
    }
    
    
    public void draw(Graphics g){
        
        for(int j = 0; j < this.col; j++){
            for(int i = 0; i < this.row; i++){
                
                int tile_num = this.getTile(i, j);
                
                switch(tile_num){
                    case 0:
                        g.setColor(Color.cyan);
                        break;
                    case 1:
                        g.setColor(Color.orange);                        
                        g.drawImage(ss.crop(1, 0, tsize, tsize), j*tsize, i*tsize, null);
                        break;                        
                    case 2:
                        g.setColor(Color.DARK_GRAY);
                        g.drawImage(ss.crop(1, 1, tsize, tsize), j*tsize, i*tsize, null);
                        break;
                    case 3:
                        g.setColor(Color.white);
                        break;
                    case 4:                        
                        g.drawImage(ss.crop(0, 1, tsize, tsize), j*tsize, i*tsize, null);
                        break;
                }
                if(tile_num != 0 && tile_num != 3){
                    //g.fillRect(j*this.tsize, i*this.tsize, this.tsize, this.tsize);
                } else if(tile_num == 3 && this.getTile(i,j-2) == 3){                
                    //desenha as nuvens
                    g.drawImage(this.getImage(nuvem_path), (j-2)*tsize, i*tsize, null);
                    
                } else {
                    //deixou de desenhar o fundo, para que entre o background                    
                }
                
                //g.setColor(Color.black);
                //g.drawString(" "+tile_num+", ("+this.getLogic(i, j) +")", j*this.tsize, i*this.tsize+20);
            }    
        }
    }
    
    public void draw(Graphics g, int col0, int colf, int row0, int rowf){
        
        for(int j = col0; j < colf; j++){
            for(int i = row0; i < rowf; i++){
                
                int tile_num = this.getTile(i, j);
                
                switch(tile_num){
                    case 0:
                        g.setColor(Color.cyan);
                        break;
                    case 1:
                        g.setColor(Color.orange);                        
                        g.drawImage(ss.crop(1, 0, tsize, tsize), j*tsize, i*tsize, null);
                        break;                        
                    case 2:
                        g.setColor(Color.DARK_GRAY);
                        g.drawImage(ss.crop(1, 1, tsize, tsize), j*tsize, i*tsize, null);
                        break;
                    case 3:
                        g.setColor(Color.white);
                        break;
                    case 4:                        
                        g.drawImage(ss.crop(0, 1, tsize, tsize), j*tsize, i*tsize, null);
                        break;
                }
                if(tile_num != 0 && tile_num != 3){
                    //g.fillRect(j*this.tsize, i*this.tsize, this.tsize, this.tsize);
                } else if(tile_num == 3 && this.getTile(i,j-2) == 3){                
                    //desenha as nuvens
                    g.drawImage(this.getImage(nuvem_path), (j-2)*tsize, i*tsize, null);
                    
                } else {
                    //deixou de desenhar o fundo, para que entre o background                    
                }
                
                //g.setColor(Color.black);
                //g.drawString(" "+tile_num+", ("+this.getLogic(i, j) +")", j*this.tsize, i*this.tsize+20);
            }    
        }
    }
    
    public Image getImage(String bg){
        ImageIcon i = new ImageIcon(getClass().getResource(bg));
        return i.getImage();        
    }
    
        public void populaMapa(){
        
        int[] temptile = {
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,
        1,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,
        1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,
        1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,
        1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,
        1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,
        1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,
        1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,1,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,1,1,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,1,1,1,1,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,
        2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2 
        };
        
        this.tile = temptile;        
        temptile = null; //destroi o vetor temporário
        
    }
}
