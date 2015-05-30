/*
A food that gives special powers to whatever fish ate it.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class PowerupHaste extends Food {
  //String type (of powerup)
  public PowerupHaste(Point2D.Double x){
    super(x);
    // load asset
		try{
			img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/food/coinHaste.png")),0.02f);
		}
		catch(Exception e){}
  }

  public void die(Fish f){
      f.haste();
      f.renew();
      isAlive = false;
      App.getOngoingGame().getFoods().remove(this);
  }
}
