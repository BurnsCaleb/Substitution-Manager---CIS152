package model;

import java.util.ArrayList;

/**
 *
 * @author burns
 */
public class Roster {
    private ArrayList<Player> players;
    private String rosterTitle = null;

    public Roster (ArrayList<Player> players) {
        this.players = players;
    }
    
    public ArrayList<Player> getRoster() {
        return players;
    }

    public void setRoster(ArrayList<Player> players) {
        this.players = players;
    }

    public String getRosterTitle() {
        return rosterTitle;
    }

    public void setRosterTitle(String rosterTitle) {
        this.rosterTitle = rosterTitle;
    }
    
    public int size () {
        return players.size();
    }
    
    public Player grabPlayer(int index) {
        return players.get(index);
    }
    
}
