/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.image.BufferedImage;

public class SpriteSheet {
 
    private BufferedImage sheet;
    private int tsize;
    
    public SpriteSheet(BufferedImage img){
        this.sheet = img;
        tsize = 32;
    }
    
    public BufferedImage crop(int col, int row, int w, int h){
        return sheet.getSubimage(tsize*col, tsize*row, w, h);
    }
    public BufferedImage crop2(int col, int row, int w, int h){
        return sheet.getSubimage(col, row, w, h);
    }    
    public BufferedImage crop3(int frame, int num_col){
        
        int col = frame % num_col;
        int row = (frame-col)/num_col;
        
        return sheet.getSubimage(tsize*col, tsize*row, tsize, tsize);
    }
    
    public BufferedImage crop4(int frame, int num_col, int w, int h){
        
        int col = frame % num_col;
        int row = (frame-col)/num_col;
        
        //int row = (int)(frame/num_col);
        //int col = (int)(frame % num_col);
        
        
        return sheet.getSubimage(w*col, h*row, w, h);
    }
}
