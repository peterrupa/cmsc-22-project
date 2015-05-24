/*
  The fish is the core entity of the game. The player needs to feed them to let them generate coins.
*/
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Fish extends Entity {
    private final int SLOW = 3;
    private final int FAST = 12;

    //Hunger is measured by the variable lifespan. Lifespan is the time before the fish dies, meaning the player has to feed the fish within this set time. If the lifespan reaches 0, the fish dies.
    private int lifespan;
    //Action performing is used for identifying whatever the fish is currently doing.
    private String actionPerforming;
    //Orientation tracks whether the fish faces left or right. This is for the proper rendering of its image.
    private String orientation;
    private String maturity;
    //Destination is the point where the fish intends to go. At idle state, a fish will go to a randomly generated point. If food is present, the fish will go to the nearest food.
  	protected Point2D.Double destination;


    Random random = new Random();

    public Fish (Point2D.Double x){
        // Constructs entity with coordinates and image

        super(x, "assets/img/fish/test.png");
        // this.speed;
        this.maturity = "hatchling";
        this.lifespan = random.nextInt(11) + 30; //30-40 seconds before dying
        this.actionPerforming = "idle";
        this.speed = SLOW;
        setDestination(new Point2D.Double(r.nextInt(App.getScreenWidth()), 200+r.nextInt(App.getScreenHeight()-200)));
        //thread for lifespan
        //thread for maturity
        //thread for movement

        imgWidth = img.getWidth();
	    imgHeight = img.getHeight();

		startThread();
        this.die();
    }

    public void releaseCoin(){
        // Released coin to App.onGoingGame
        // Pass current location and value (based on maturity level)
    }

    //Updates the destination point of the fish.
    public void setDestination(Point2D.Double destination){
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

    public void update() {
        // Search for nearby foods
        Point2D.Double nearestFood = findNearestFood();

        if(nearestFood != null){
            // Set destination location to the nearest food
            this.destination.setLocation(nearestFood.getX(), nearestFood.getY());
            this.actionPerforming = "food";
            this.speed = FAST;

        }
        else if(nearestFood == null && actionPerforming == "food"){
            // Case when from "food" to "idle"
            this.actionPerforming = "idle";
            this.speed = SLOW;
            setRandomDestination();
        }

        // Updating the direction used for image rendering
        double x = this.position.getX(), y = this.position.getY();
    	double x2 = this.destination.getX(), y2 = this.destination.getY();
    	double dx = x2 - x, dy = y2 - y;
        direction = Math.atan2(dy,dx) * 180 / Math.PI;

    	// moving the fish
        // updates position
        x += this.speed * Math.cos(Math.toRadians(direction));  // x-position
        y += this.speed * Math.sin(Math.toRadians(direction));  // y-position
        this.position.setLocation(x, y);

    	// check if fish is at the destination point
        if(x <= x2 + speed && x >= x2 - speed && y <= y2 + speed && y >= y2 - speed){
            setRandomDestination();
        }
    }

    // Returns the point of the nearest food. If none, returns null.
    private Point2D.Double findNearestFood(){
        ArrayList<Food> foods = App.getOngoingGame().getFoods();
        Point2D.Double nearestPoint = null;
        double x1 = this.position.getX(), y1 = this.position.getY();
        for(int i = 0; i < foods.size(); i++){
            Food current = foods.get(i);

            if(nearestPoint == null || this.getDistance(this.getPosition(), current.getPosition()) < this.getDistance(this.getPosition(), nearestPoint))
                nearestPoint = current.getPosition();
        }
        return nearestPoint;
    }

    // Computes for the distance between the two given points.
    private double getDistance(Point2D.Double p, Point2D.Double q){
        double x1 = p.getX(), y1 = p.getY(), x2 = q.getX(), y2 = q.getY();
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    // Sets the fish destination to a new random point.
    private void setRandomDestination(){
        double newPointX = r.nextInt(App.getScreenWidth());
        double newPointY = r.nextInt(App.getScreenHeight()) + 200;

        this.destination.setLocation(newPointX, newPointY);
    }
}
