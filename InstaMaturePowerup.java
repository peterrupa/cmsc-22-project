/*
  A food that gives special powers to whatever fish ate it.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public abstract class InstaMaturePowerup extends Powerup {
  //String type (of powerup)
  public InstaMaturePowerup(Point2D.Double x){
    super(x);
  }

  public void effect(Fish f){
    f.mature();
  }
}
