/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

/**
 *
 * @author Marcio
 */
public interface Subject {
    
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notify(String s);
    
}
