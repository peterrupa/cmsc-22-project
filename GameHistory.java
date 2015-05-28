/*
A game history object that stores the player name and its corresponding game points.
*/
import java.io.*;
import java.util.*;


@SuppressWarnings("serial") //make the linter/compiler shut up
public class GameHistory implements Serializable {

    private ArrayList<Player> players;

    public GameHistory(){
        this.players = new ArrayList<Player>();
    }

    public ArrayList<Player> getTopThree() {
        // returns top3 in the form of an arrayList
    }

    public void addPlayer(Player x) {
        this.players.add(x);
    }
}
