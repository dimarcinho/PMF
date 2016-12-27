/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boss_states;

import objects.Animated;
import objects.Player;

/**
 *
 * @author Marcio
 */
public abstract class Boss extends Animated {
    
    public Player p;
    public int g;
    public int jumpspeed;
    public int lifepoints;
    public boolean isInvencible;
    
    public Boss(int x, int y, Player p){
        this.x = x;
        this.y = y;
        this.p = p;
    }

}
