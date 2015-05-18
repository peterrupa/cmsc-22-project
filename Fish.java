/*
  The fish is the core entity of the game. The player needs to feed them to let them generate coins.
*/

public class Fish extends Entity {
    //Hunger is measured by the variable lifespan. Lifespan is the time before the fish dies, meaning the player has to feed the fish within this set time. If the lifespan reaches 0, the fish dies.
  //int lifespan

    //Action performing is used for identifying whatever the fish is currently doing.
  //String actionPerforming

    //Destination is the point where the fish intends to go. At idle state, a fish will go to a randomly generated point. If food is present, the fish will go to the nearest food.
  //Point destination

    //Orientation tracks whether the fish faces left or right. This is for the proper rendering of its image.
  //String orientation

    //Time to mature is the time until the fish reaches the next state of maturity.
  //int timeToMature

    //Release coin creates a new coin on the map at the fish's position.
  //releaseCoin()

    //Updates the destination point of the fish.
  //setDestination()

    //Eats the food.
  //eat(Food f);

    //Returns the position point of the fish.
  //getPos();

    //Method called when the fish dies. Will contain timer cancels.
  //die();
}
