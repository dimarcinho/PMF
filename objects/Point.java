/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Marcio
 */
public class Point {
    
    public int x, y;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;        
    }
    
    public float distancePoint(Point a){
        
        double d = 0;
        
        d = Math.sqrt((this.x-a.x)^2 + (this.y-a.y)^2);
        
        return (float)d;
    }
    
    public float distXYPoint(int x, int y){
        
        double d = 0;
        
        d = Math.sqrt((this.x-x)^2 + (this.y-y)^2);
        
        return (float)d;
    }
    
    public void setXY(int x, int y){
        this.x = x;
        this.y = y; 
    }
}
