import java.io.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Player implements Serializable {

private String name;
private int score;

private int totalFishBought;
private int fishAlive;  //alive by the end of the game
private int fishDied;

private int coinsSpent;
private int foodBought;
private int foodUsed;

private int powerupInstaMatureBought;
private int powerupInstaMatureUsed;
private int powerupDoubleCoinsBought;
private int powerupDoubleCoinsUsed;
private int powerupNullHungerBought;
private int powerupNullHungerUsed;
private int powerupHasteBought;
private int powerupHasteUsed;

private int gameTime;  //possible if tank empties before 5 minutes is over. Does not count pauses.

public Player(String name, int score, int totalFishBought, int fishAlive, int fishDied, int coinsSpent, int foodBought, int foodUsed, int powerupInstaMatureBought,
       int powerupInstaMatureUsed, int powerupDoubleCoinsBought, int powerupDoubleCoinsUsed, int powerupNullHungerBought, int powerupNullHungerUsed, int powerupHasteBought,
       int powerupHasteUsed, int gameTime) {
    // GG Code
    this.name = name;
    this.score = score;
    this.totalFishBought = totalFishBought;
    this.fishAlive = fishAlive;
    this.fishDied = fishDied;
    this.coinsSpent = coinsSpent;
    this.foodBought = foodBought;
    this.foodUsed = foodUsed;
    this.powerupInstaMatureBought = powerupInstaMatureBought;
    this.powerupInstaMatureUsed = powerupInstaMatureUsed;
    this.powerupDoubleCoinsBought = powerupDoubleCoinsBought;
    this.powerupDoubleCoinsUsed = powerupDoubleCoinsUsed;
    this.powerupNullHungerBought = powerupNullHungerBought;
    this.powerupNullHungerUsed = powerupNullHungerUsed;
    this.powerupHasteBought = powerupHasteBought;
    this.powerupHasteUsed = powerupHasteUsed;
    this.gameTime = gameTime;

}

public Player (String name, int score) {
    this.name = name;
    this.score = score;
}

public String getName(){
    return this.name;
}

public int getScore() {
    return this.score;
}

public String toString() {
    return "Name: " + this.name + " Score: " + this.score + "\n";
}

}
