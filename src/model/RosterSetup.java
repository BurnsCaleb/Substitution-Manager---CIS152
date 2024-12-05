package model;

/**
 *
 * @author burns
 */
public class RosterSetup {

    private int rosterSize;

    // CONSTRUCTORS
    // Standard 15 Man Roster
    public RosterSetup() {
        rosterSize = 15;
    }

    public RosterSetup(int rosterSize) {
        this.rosterSize = rosterSize;
    }

    // GETTERS SETTERS
    public int getRosterSize() {
        return rosterSize;
    }

    public void setRosterSize(int rosterSize) {
        this.rosterSize = rosterSize;
    }

    // HELPER METHODS
    @Override
    public String toString() {
        return "Roster Size: " + getRosterSize() + ".";
    }
}
