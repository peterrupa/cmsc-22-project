/*
  The fish is the core entity of the game. The player needs to feed them to let them generate coins.
*/
import java.util.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Fish extends Entity {
    //Hunger is measured by the variable lifespan. Lifespan is the time before the fish dies, meaning the player has to feed the fish within this set time. If the lifespan reaches 0, the fish dies.
    private int lifespan;

    //Action performing is used for identifying whatever the fish is currently doing.
    private String actionPerforming;
    //Orientation tracks whether the fish faces left or right. This is for the proper rendering of its image.
    private String orientation;
    private int maturity;

    Random random = new Random();

    public Fish (Point x, JLabel y){
        // Constructs entity with coordinates and image
        super(x,y);
        this.lifespan = random.nextInt(10) + 30; //30-40 seconds b4 dying
    }

    public void releaseCoin(){
        // Released coin to App.onGoingGame
        // Pass current location and value (based on maturity level)
    }

    //Updates the destination point of the fish.
    public void setDestination(Point destination){
        this.destination = destination;
    }

    public void eat(Food f){

    }
    public void die(){
        // Death animation + kill all threads
    }
}
