package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author burns
 */
public class Bench {
    private Queue<Player> bench = new LinkedList<>();
    
    public Bench(ArrayList<Player> players) {
        for(Player p : players) {
            bench.add(p);
        }
    }

    public Queue<Player> getBench() {
        return bench;
    }

    public void setBench(Queue<Player> bench) {
        this.bench = bench;
    }
    
    public void add(Player player) {
        bench.add(player);
    }
    
    public void remove(Player player) {
        bench.remove(player);
    }
}
