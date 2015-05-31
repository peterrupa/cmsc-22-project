/*
The fish is the core entity of the game. The player needs to feed them to let them generate coins.
*/
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Fish extends Entity {
  private final int SLOW = 3; //3
  private final int FAST = 7; //7
  private final double FOOD_ZONE_MODIFIER = 0.7;
  private final double FISH_EAT_ZONE_MODIFIER = 3;

  private final float HATCHLING_SIZE = 0.0431f;
  private final float JUVENILE_SIZE = 0.0631f;
  private final float ADULT_SIZE = 0.0831f;

  private boolean isMainMenu; //true if it is a fish found in the Main Menu
  private int age;
  private int maturePoint; //age when fish shall mature;
  private int coinTimer; // age when fish shall spitCoin
  private String maturity;
  //Hunger is measured by the variable lifespan. Lifespan is the time before the fish dies, meaning the player has to feed the fish within this set time. If the lifespan reaches 0, the fish dies.
  private int lifespan; //how many seconds before fish dies
  //Action performing is used for identifying whatever the fish is currently doing.
  private String actionPerforming;
  private int hungerNulledTimer;
  	//attribute to show hunger is nullified for the powerup
  private int coinModifyTimer;
  	//timer for duration of double coin power up
  private int coinValueModifier;
  	//attribute to increase coin value when powerup is used

  //Destination is the point where the fish intends to go. At idle state, a fish will go to a randomly generated point. If food is present, the fish will go to the nearest food.
  protected Point2D.Double destination;

  private BufferedImage open_mouth = null;
  private BufferedImage open_mouth_inverted = null;
  private BufferedImage closed_mouth = null;
  private BufferedImage closed_mouth_inverted = null;

  private static BufferedImage hatchling_closed_mouth = null;
  private static BufferedImage hatchling_open_mouth = null;
  private static BufferedImage hatchling_closed_mouth_inverted = null;
  private static BufferedImage hatchling_open_mouth_inverted = null;

  private static BufferedImage juvenile_closed_mouth = null;
  private static BufferedImage juvenile_open_mouth = null;
  private static BufferedImage juvenile_closed_mouth_inverted = null;
  private static BufferedImage juvenile_open_mouth_inverted = null;

  private static BufferedImage adult_closed_mouth = null;
  private static BufferedImage adult_open_mouth = null;
  private static BufferedImage adult_closed_mouth_inverted = null;
  private static BufferedImage adult_open_mouth_inverted = null;


  final Random random = new Random();

  public Fish (Point2D.Double x, boolean xa){
    // Constructs entity with coordinates and image
    super(x);
    this.actionPerforming = "idle";
    this.isMainMenu = xa;
    this.initialize();
  }

  public Fish (boolean xa){
    super();
    this.actionPerforming = "idle";
    this.isMainMenu = xa;
    this.initialize();
  }

  // public Fish(Point2D.Double x,String name){
  //   super();
  //   this.actionPerforming = "MainMenu";
  //   this.initialize();
  // }

  public void initialize(){
    // load images if not yet loaded
    if(hatchling_closed_mouth == null || hatchling_open_mouth == null || hatchling_closed_mouth_inverted == null || hatchling_open_mouth_inverted == null){
      try{
        //hatchling
        hatchling_closed_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/hatchling_close.png")), HATCHLING_SIZE);
        hatchling_open_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/hatchling_open.png")), HATCHLING_SIZE);
        hatchling_closed_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/hatchling_close_i.png")), HATCHLING_SIZE);
        hatchling_open_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/hatchling_open_i.png")), HATCHLING_SIZE);

        //juvenile
        juvenile_closed_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/juvenile_close.png")), JUVENILE_SIZE);
        juvenile_open_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/juvenile_open.png")), JUVENILE_SIZE);
        juvenile_closed_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/juvenile_close_i.png")), JUVENILE_SIZE);
        juvenile_open_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/juvenile_open_i.png")), JUVENILE_SIZE);

        //adult
        adult_closed_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/adult_close.png")), ADULT_SIZE);
        adult_open_mouth = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/adult_open.png")), ADULT_SIZE);
        adult_closed_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/adult_close_i.png")), ADULT_SIZE);
        adult_open_mouth_inverted = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/adult_open_i.png")), ADULT_SIZE);
      }
      catch(Exception e){}
    }

    this.img = hatchling_closed_mouth;
    this.imgWidth = img.getWidth();
    this.imgHeight = img.getHeight();

    //set fish assets to hatchling assets
    setAsset("hatchling");

    this.age = 0; //age starts at 0
    this.maturePoint = App.FRAME_RATE*(age + random.nextInt(21) + 40); // maturity will occur 40-60 seconds later
    this.maturity = "hatchling";
    this.coinTimer = App.FRAME_RATE*(20 + random.nextInt(11)); //first coin will spawn 20-30 seconds later
    this.lifespan = App.FRAME_RATE*(random.nextInt(11) + 30); //30-40 seconds before dying

    this.speed = SLOW;
    this.hungerNulledTimer = 0;
      //timer for nullified hunger set to 0 seconds at start
    this.coinModifyTimer = 0;
      //timer for double coin powerup
    this.coinValueModifier = 1;
      //multiplier for double coin powerup

    double newPointX = r.nextInt(App.getScreenWidth() - (int)this.getWidth()) + this.getWidth() / 2;
    double newPointY = r.nextInt(App.getScreenHeight() - (int)(App.getScreenHeight() * 0.186f) - (int)this.getWidth()) + this.getHeight() / 2;
    //this.destination = ;
    setDestination(new Point2D.Double(newPointX, newPointY));

    startThread();

  }

  public void releaseCoin(){
    // Released coin to App.onGoingGame
    int coinValue = 0;

    switch(maturity){
      case "hatchling":
      coinValue = 1;
      break;
      case "juvenile":
      coinValue = 3;
      break;
      case "adult":
      coinValue = 5;
      break;
    }

    Point2D.Double coinPos = new Point2D.Double(this.getPosition().getX(), this.getPosition().getY());
    App.getOngoingGame().getCoins().add(new Coin(coinPos, coinValue*coinValueModifier));
    coinTimer = age + 20*App.FRAME_RATE + random.nextInt(11)*App.FRAME_RATE; //next coin will spawn 20-30 seconds later
    // Pass current location and value (based on maturity level)
  }

  //Updates the destination point of the fish.
  public void setDestination(Point2D.Double destination){
    this.destination = destination;
  }

  public void eat(Food f){
    f.die(this);
    //reset proper image if it was hungry
  }
  public void die() {
    isAlive = false;
    //cancel all threads
    //create death animation effect (or smoke puff) at current position
    //remove from ongoing game fish list
    App.getOngoingGame().getFish().remove(this);
  }

  //sets the maturity one level up
  public void mature(){
    //cute particle here (smoke effect? or sparks? glitters?)
    switch(maturity){
      case "hatchling":
        maturity = "juvenile";
        setAsset("juvenile");
        break;
      case "juvenile":
        maturity = "adult";
        setAsset("adult");
        break;
    }
    System.out.println(this+" is maturing to "+maturity+"!");
    maturePoint = (age + random.nextInt(21)*App.FRAME_RATE + 40*App.FRAME_RATE); //fish shall mature 40-60 seconds later
  }

  private void setAsset(String s){
    switch(s){
      case "hatchling":
        open_mouth = hatchling_open_mouth;
        open_mouth_inverted = hatchling_open_mouth_inverted;
        closed_mouth = hatchling_closed_mouth;
        closed_mouth_inverted = hatchling_closed_mouth_inverted;
        break;
      case "juvenile":
        open_mouth = juvenile_open_mouth;
        open_mouth_inverted = juvenile_open_mouth_inverted;
        closed_mouth = juvenile_closed_mouth;
        closed_mouth_inverted = juvenile_closed_mouth_inverted;
        break;
      case "adult":
        open_mouth = adult_open_mouth;
        open_mouth_inverted = adult_open_mouth_inverted;
        closed_mouth = adult_closed_mouth;
        closed_mouth_inverted = adult_closed_mouth_inverted;
        break;
    }
    img = closed_mouth;
    this.imgWidth = img.getWidth();
    this.imgHeight = img.getHeight();
  }

  public void update() {
    //   System.out.println("Updating");
      // Search for nearby foods
      if(this.age == this.maturePoint ) { //Maturing
          mature();
      }
      if(this.age == this.coinTimer) { //On releasing coins
          releaseCoin();
      }
      if(this.lifespan < 0){
          die();
      }

      Food nearestFood = findNearestFood();
      double fishX = this.getPosition().getX(), fishY = this.getPosition().getY();

      if(nearestFood != null){
          // Set destination location to the nearest food
          this.destination.setLocation(nearestFood.getPosition().getX(), nearestFood.getPosition().getY());
          this.actionPerforming = "food";
          this.speed = FAST;

          double fishEatLeftBound = fishX + (imgWidth / 2) * FISH_EAT_ZONE_MODIFIER , fishEatRightBound = fishX - (imgWidth / 2) * FISH_EAT_ZONE_MODIFIER;
          double fishEatUpBound = fishY - (imgHeight / 2) * FISH_EAT_ZONE_MODIFIER, fishEatDownBound = fishY + (imgHeight / 2) * FISH_EAT_ZONE_MODIFIER;

          double foodX = nearestFood.getPosition().getX(), foodY = nearestFood.getPosition().getY();
          double foodLeftBound = foodX + (nearestFood.getWidth() / 2) - (nearestFood.getWidth() / 2) * FOOD_ZONE_MODIFIER, foodRightBound = foodX - (nearestFood.getWidth() / 2) + (nearestFood.getWidth() / 2) * FOOD_ZONE_MODIFIER;
          double foodUpBound = foodY - (nearestFood.getHeight() / 2) + (nearestFood.getHeight() / 2) * FOOD_ZONE_MODIFIER, foodDownBound = foodY + (nearestFood.getHeight() / 2) - (nearestFood.getHeight() / 2) * FOOD_ZONE_MODIFIER;

          // check if food is within eating range

          if(fishEatLeftBound >= foodRightBound && fishEatRightBound <= foodLeftBound && fishEatDownBound >= foodUpBound && fishEatUpBound <= foodDownBound){
              // change img to open mouth
              if(getDirection()>=90 || getDirection()<-90) { //check direction if we need to flip
                  openMouthInverted();
              } else {
                  openMouth();
              }

          }
          else{
              if(this.getDirection()>=90 || this.getDirection()<-90) { //check direction if we need to flip
                  closedMouthInverted();
              } else {
                  closeMouth();
              }
          }
      }
      else if(nearestFood == null && actionPerforming == "food"){
          // Case when from "food" to "idle"
          this.actionPerforming = "idle";
          this.speed = SLOW;
          // change img to close mouth
          if(this.getDirection()>=90 || this.getDirection()<-90) { //check direction if we need to flip
              closedMouthInverted();
          } else {
              closeMouth();
          }
          setRandomDestination();
      } else {
          if(this.getDirection()>=90 || this.getDirection()<-90) { //check direction if we need to flip
              closedMouthInverted();
          } else {
              closeMouth();
          }
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

      // check if there's a collision between fish and a food
      ArrayList<Food> foods = App.getOngoingGame().getFoods();
      for(int i = 0; i < foods.size(); i++){
          //setting boundatries for fish collision with food
          Food current = foods.get(i);
          double fishLeftBound = fishX + (imgWidth / 2), fishRightBound = fishX - (imgWidth / 2);
          double fishUpBound = fishY - (imgHeight / 2), fishDownBound = fishY + (imgHeight / 2);

          double foodX = current.getPosition().getX(), foodY = current.getPosition().getY();
          double foodLeftBound = foodX + (current.getWidth() / 2) - (current.getWidth() / 2) * FOOD_ZONE_MODIFIER, foodRightBound = foodX - (current.getWidth() / 2) + (current.getWidth() / 2) * FOOD_ZONE_MODIFIER;
          double foodUpBound = foodY - (current.getHeight() / 2) + (current.getHeight() / 2) * FOOD_ZONE_MODIFIER, foodDownBound = foodY + (current.getHeight() / 2) - (current.getHeight() / 2) * FOOD_ZONE_MODIFIER;

          // check if food is within eating range
          if(fishLeftBound >= foodRightBound && fishRightBound <= foodLeftBound && fishDownBound >= foodUpBound && fishUpBound <= foodDownBound){
              Utilities.playSFX("assets/sounds/sfx/bite_"+this.maturity+".wav");
              this.eat(current);
          }
      }

      // check if fish is at the destination point
      if(x <= x2 + speed && x >= x2 - speed && y <= y2 + speed && y >= y2 - speed){
          setRandomDestination();
      }
      if(!(this.isMainMenu())) {
          //update fish statistics
          this.age+=1;
          if(hungerNulledTimer <= 0) { //for abstraction
              this.lifespan-=1;
          } else {
              hungerNulledTimer-=1;
          }
          if(coinModifyTimer > 0) {
              coinValueModifier = 2;
              coinModifyTimer-=1;
          } else {
              coinValueModifier = 1;
          }
      }

  }

  public void convertToGameFish() {
      this.isMainMenu = false;
  }

  public boolean isMainMenu() {
      return isMainMenu;
  }

  public void nullHunger() {
    this.hungerNulledTimer = App.FRAME_RATE*(30);
  }

  public void doubledCoins() {
    this.coinModifyTimer = App.FRAME_RATE*(30);
  }

  //  Functions that change image to render/rotate. Please
  private void openMouth(){
    img = open_mouth;
  }

  private void closeMouth(){
    img = closed_mouth;
  }

  private void openMouthInverted(){
    img = open_mouth_inverted;
  }

  private void closedMouthInverted(){
    img = closed_mouth_inverted;
  }

  // Returns the point of the nearest food. If none, returns null.
  private Food findNearestFood(){
    ArrayList<Food> foods = App.getOngoingGame().getFoods();
    Food nearestPoint = null;
    double x1 = this.position.getX(), y1 = this.position.getY();
    if(foods.size() > 0) {
      for(int i = 0; i < foods.size(); i++){
        Food current = foods.get(i);

        if(nearestPoint == null || this.getDistance(this.getPosition(), current.getPosition()) < this.getDistance(this.getPosition(), nearestPoint.getPosition()))
        nearestPoint = current;
      }
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
    double newPointX = r.nextInt(App.getScreenWidth() - (int)this.getWidth()) + this.getWidth() / 2;
    double newPointY = r.nextInt(App.getScreenHeight() - (int)(App.getScreenHeight() * 0.186f) - (int)this.getWidth()) + this.getHeight() / 2;

    this.destination.setLocation(newPointX, newPointY);
  }

  public void renew() {
    this.lifespan = App.FRAME_RATE*(random.nextInt(11)+30);
  }

  public int getLifespan(){
    return lifespan/App.FRAME_RATE;
  }
}
