package model;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author burns
 */
public class GameSetup {

    private int numQuarters;
    private int quarterLength;
    private int currQuarter = 1;
    private int gameSecs = 0;
    private int quarterLengthInSecs;

    // List of all the game times a sub was made
    // This way I can get the correct time a player was in the game
    private LinkedList<Integer> subTimes = new LinkedList<>();

    // CONSTRUCTORS 
    public GameSetup(int numQuarters, int quarterLength) {
        this.numQuarters = numQuarters;
        this.quarterLength = quarterLength;

        setGameSecs();
        setQuarterLengthInSecs();
        
        subTimes.add(gameSecs);
    }

    // GETTERS SETTERS
    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }

    public int getQuarterLength() {
        return quarterLength;
    }

    public void setQuarterLength(int quarterLength) {
        this.quarterLength = quarterLength;
    }

    public LinkedList<Integer> getSubTimes() {
        return subTimes;
    }

    /*
    Return last sub time
    If list is empty, no sub has been made so clock is at quarter length
     */
    public int getLastSubTime() {
        try {
            return subTimes.getLast();
        } catch (NoSuchElementException ns) {
            return gameSecs;
        } catch (NullPointerException np) {
            return gameSecs;
        }

    }

    public void setSubTimes(LinkedList<Integer> subTimes) {
        this.subTimes = subTimes;
    }

    public void addSubTime(int subTime) {
        subTimes.addLast(subTime);
    }

    public int getTotalGameTime() {
        return (getNumQuarters() * getQuarterLength());
    }

    public int getGameSecs() {
        return gameSecs;
    }

    // Seconds from Last Sub or start of game if no subs made yet
    public int getSecsPlayed(int quarter, int mins, int secs, int prevSub) {
        if (gameSecs != 0) {

            // If no previous sub, previous sub = game start
            if (prevSub == 0) {
                prevSub = gameSecs;
            }
            int timeInSecs = (mins * 60) + secs;
            int currSub = timeInSecs + (quarterLengthInSecs * (numQuarters - quarter)); // Time Left in game in seconds at time of sub

            return prevSub - currSub; // Time in seconds since last sub for player time in game
        } else {
            return 0;
        }
    }

    private void setGameSecs() {
        this.gameSecs = (getQuarterLength() * getNumQuarters()) * 60;
    }

    public int getQuarterLengthInSecs() {
        return quarterLengthInSecs;
    }

    private void setQuarterLengthInSecs() {
        this.quarterLengthInSecs = (getQuarterLength() * 60);
    }

    public int getCurrQuarter() {
        return currQuarter;
    }

    public void setCurrQuarter(int currQuarter) {
        this.currQuarter = currQuarter;
    }

}
