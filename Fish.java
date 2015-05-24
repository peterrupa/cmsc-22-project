/*
  The fish is the core entity of the game. The player needs to feed them to let them generate coins.
*/
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Fish extends Entity {
    //Hunger is measured by the variable lifespan. Lifespan is the time before the fish dies, meaning the player has to feed the fish within this set time. If the lifespan reaches 0, the fish dies.
    private int lifespan;

    //Action performing is used for identifying whatever the fish is currently doing.
    private String actionPerforming;
    //Orientation tracks whether the fish faces left or right. This is for the proper rendering of its image.
    private String orientation;
    private String maturity;
    //Destination is the point where the fish intends to go. At idle state, a fish will go to a randomly generated point. If food is present, the fish will go to the nearest food.
  	protected Point destination;

    Random random = new Random();

    public Fish (Point2D.Double x){
        // Constructs entity with coordinates and image
        super(x);
        this.maturity = "hatchling";
        this.lifespan = random.nextInt(11) + 30; //30-40 seconds before dying

        //thread for lifespan
        //thread for maturity
        //thread for movement
    }

    public Fish (){
        // Constructs entity with coordinates and image
        super(new Point2D.Double(100, 100));
        this.maturity = "hatchling";
        this.lifespan = random.nextInt(11) + 30; //30-40 seconds before dying

        //thread for lifespan
        //thread for maturity
        //thread for movement
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
      //food die(this)
      //set new destination if no food
      //reset proper image if it was hungry
    }
    public void die(){
        //cancel all threads
        //create death animation effect (or smoke puff) at current position
        //remove from ongoing game fish list
    }

    //sets the maturity one level up
    public void mature(){
      //cute particle here (smoke effect? or sparks? glitters?)
      switch(maturity){
        case "hatchling":
          maturity = "juvenile";
          break;
        case "juvenile":
          maturity = "adult";
          break;
      }
    }
}
