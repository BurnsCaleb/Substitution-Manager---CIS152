package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author burns
 */
public class Court {
    private LinkedList<Player> onCourt;
    
    public Court(ArrayList<Player> players) {
        if (players.size() == 5) {
            onCourt = new LinkedList<>();
            for (Player p : players) {
                onCourt.push(p);
            }
        }
    }

    public LinkedList<Player> getOnCourt() {
        return onCourt;
    }

    public void setOnCourt(LinkedList<Player> onCourt) {
        this.onCourt = onCourt;
    }
    
    public void push(Player player) {
        onCourt.push(player);
    }
    
    public void pop() {
        onCourt.pop();
    }
    
    public void peek() {
        onCourt.peek();
    }
    
    public void size() {
        onCourt.size();
    }
}
