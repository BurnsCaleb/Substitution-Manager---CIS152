package model;

/**
 *
 * @author burns
 */
public class Player {
    private String playerName;
    private double minsPlayed;
    
    public Player(String playerName) {
        this.playerName = playerName;
        minsPlayed = 0.0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getMinsPlayed() {
        return minsPlayed;
    }

    public void setMinsPlayed(double minsPlayed) {
        this.minsPlayed = minsPlayed;
    }
    
    public void updateMinsPlayed(double minsPlayed) {
        this.minsPlayed += minsPlayed;
    }
    
}
