/*
  A food that gives special powers to whatever fish ate it.
*/

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Powerup extends Food {
  //String type (of powerup)
  public Powerup(Point x, JLabel y){
    super(x, y);
  }
}
