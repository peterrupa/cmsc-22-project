/*
  A food that gives special powers to whatever fish ate it.
  - *Power ups
    - Super food
      - Pause Hunger
      - Double coins
      - Insta mature
      - Haste (speed and coin generation)
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public abstract class Powerup extends Food {
  //String type (of powerup)
  public Powerup(Point2D.Double x){
    super(x);

    // load asset
  }

  public abstract void effect(Fish f);

  @Override
  public void die(Fish f){
    //refresh lifespan value
    //effect(f);
    //remove from ongoing game food list
  }
}
