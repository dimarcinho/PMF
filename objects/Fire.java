/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Marcio
 */
public class Fire {
    
    private int x, y, grid;
    private int size, intensity;
    
    private int arraySize;
    
    public int[][] fire;
    
    public Random rnd;
    
    private ArrayList<Color> colors;
    
    public Fire(int x, int y, int size, int intensity){
        
        this.x = x;
        this.y = y;
        this.size = size;
        this.intensity = intensity;
        
        rnd = new Random();
        
        grid = 2;
        arraySize = size/grid;
        
        fire = new int[arraySize+1][arraySize+1];
        for(int i = 0; i < arraySize; i++){
            for(int j = 0; j < arraySize; j++){
                fire[i][j] = 0;
            }
        }

        
        //Cria o array cores
        colors = new ArrayList<>();
        for(x = 0; x < 100; x++){
            int red, green, blue;
            red = (x)*(255-224)/(100) + 224;
            green = (x)*(255-104)/(100) + 104;
            blue = (x)*(193-39)/(100) + 39;
            
            red = (x)*(255)/(100);
            green = (x)*(255-100)/(100);
            blue = (x)*(255-200)/(100); 
            colors.add(new Color(red, green, blue,255));
            /*
            if(x < 10){
                colors.add(new Color(red, green, blue,0));
            } else {
                colors.add(new Color(red, green, blue,255));
            }
            */
        }
    }
    
    public void update(){
        
        for(int i = 0; i < arraySize; i++){
            fire[arraySize][i] = rnd.nextInt(100);
        }
        
        for(int i = arraySize - 1; i > 0; i--){
            for(int j = 1; j < (arraySize-1); j++){                
                fire[i][j] = (fire[i][j]+
                                fire[i+1][j-1]+
                                fire[i+1][j]+
                                fire[i+1][j+1])/4;                
            }
        }
        
        
    }
    
    public void draw(Graphics g){
        for(int i = 0; i < arraySize-1; i++){
            for(int j = 0; j < arraySize; j++){
                g.setColor(colors.get(fire[i][j]));
                g.fillRect(x+grid*j, y+grid*i, grid, grid);
            }
        }
    }
}
