/*
  A game history object that stores the player name and its corresponding game points.
*/

public class GameHistory {
  private String playerName;
  private int points;

  public GameHistory(String playerName, int points){
    this.playerName = playerName;
    this.points = points;
  }

  public String getPlayerName(){
    return playerName;
  }

  public int getPoints(){
    return points;
  }
}
