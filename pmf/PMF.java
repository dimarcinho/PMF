/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pmf;

import java.awt.Color;
import javax.swing.JFrame;

public class PMF {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    
    //usar tiles de Y x Y

    public static void main(String[] args) {

        JFrame f = new JFrame();
        f.pack();        
        f.setSize(WIDTH, HEIGHT);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("PlataMFormer");
        f.setBackground(Color.black);
        f.add(new GamePanel());  
        f.setVisible(true);

    }
}
