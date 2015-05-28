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
        Player first;
        Player second;
        Player third;
        ArrayList<Player> temp = new ArrayList<Player>();

        if(players.size()<0) {
            first = players.get(0);
            for(Player x : players) {
                if(x.getScore()>first.getScore()) {
                    first = x;
                }
            }
        }

        // if(players.size()<0) {
        //     for(Player x : players) {
        //         if(x.getScore()<=first.getScore() && x!=first) {
        //             second = x;
        //         }
        //     }
        // }
        return temp;
    }

    public void addPlayer(Player x) {
        this.players.add(x);
    }

    public void printPlayers() {
        for(Player x: players) {
            System.out.println(x.toString());
        }
    }
}
