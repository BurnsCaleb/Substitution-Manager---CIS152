package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author burns
 */
public class RosterIO {

    private static ArrayList<Player> players = null;

    private static List<Roster> rosters = null;

    private static Path filePath = Paths.get("src/main/resources/data/rosters.txt");

    public static List<Roster> selectAllRosters() {   //Return a list of Roster objects
        rosters = new ArrayList<>();

        try {

            Files.lines(filePath).forEach(line -> {

                players = new ArrayList<>();
                String[] playerNames = line.split("\\|");

                for (String playerName : playerNames) {
                    players.add(new Player(playerName.trim()));
                }

                Roster r = new Roster(players);
                rosters.add(r);
            });

            for (int i = 0; i < rosters.size(); i++) {    // Name Rosters
                rosters.get(i).setRosterTitle("Roster " + (i + 1));
            }
            return rosters;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void save(ArrayList<String> names) {
        try {
            FileWriter myWriter = new FileWriter(filePath.toString(), true);
            int size = names.size();
            for (int i = 0; i<size; i++) {
                myWriter.write(names.get(i).trim());
                if (i+1 != size){
                    myWriter.write(" | ");
                }
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

