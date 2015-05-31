/*
A game history object that stores the player name and its corresponding game points.
*/
import java.io.*;
import java.util.*;


@SuppressWarnings("serial") //make the linter/compiler shut up
public class GameHistory implements Serializable {

    private ArrayList<Player> players;
    private ArrayList<Player> topFive;

    public GameHistory(){
        this.players = new ArrayList<Player>();
    }

    public ArrayList<Player> getTopFive() {
        // returns top3 in the form of an arrayList
        topFive = new ArrayList<Player>();

        for(int i = 0 ; players.size()!=0 && i<5 ; i++) {
            //get max score
            int maxScore = 0;
            for(Player x : players) {
                if(x.getScore() > maxScore) {
                    maxScore = x.getScore();
                }
            }
            //add all with max score to topFive
            for(Player f : players) {
                if(f.getScore() == maxScore) {
                    System.out.println("Rank " + Integer.toString((i+1)) + ": " + f.toString());
                    topFive.add(f);
                }
            }
            for(Player f : topFive) {
                players.remove(f);
            }
        }

        try {
            return topFive;
        } finally {
            // Ariel happened to me
            System.out.println("Ariel is happening to me!");
            for(Player x: topFive) {
                x.toString();
                players.add(x);
            }
            for(Player f : players) {
                topFive.remove(f);
            }
        }

    }

    public void addPlayer(Player player) {
        // readd top5 to players list for recalculation
        players.add(player);
    }

    public void printPlayers() {
        for(Player x: players) {
            System.out.println(x.toString());
        }
    }

    public String getPlayer(int x) {
        return players.get(x).toString();
    }

    public void purge(int f) {
        for(Player x: players) {
            if(x.getScore() == f) {
                players.remove(x);
            }
        }
    }
}
