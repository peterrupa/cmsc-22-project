/*
  Food is an important element in the game. This is the personal goal of the fish, wherein it replenishes their lifespan.
*/
  /*
	Food is extendable, and has special features (to be extended)
	Powerups extend food, and they feed the fish as well.

	Kinds of powerups:
	  - Pause Hunger
	  - Double coins
	  - Insta mature
	  - Haste (speed and coin generation)
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Food extends Entity {
  public Food(Point2D.Double x){
    super(x);
  }

  public void die(){
    //create something particle at current position
    //remove from ongoing game food list
  }

  //when eaten by a fish
  public void die(Fish f){
    //refresh lifespan value
    //remove from ongoing game food list
  }
}
