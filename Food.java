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
import javax.imageio.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Food extends Entity {
public Food(Point2D.Double x) {
    super(x);
    this.speed = 2;

    try{
        img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/food/food.png")), 0.02f);
    }
    catch(Exception e) {}

    imgWidth = img.getWidth();
    imgHeight = img.getHeight();
    startThread();
}

public void die() {
    //create something particle at current position
    isAlive = false;
    App.getOngoingGame().getFoods().remove(this);
}

//when eaten by a fish
public void die(Fish f) {
    //refresh lifespan value
    f.renew();
    isAlive = false;
    App.getOngoingGame().getFoods().remove(this);
    // System.out.println("Food die!");
}
public void update() {
    double x = this.position.getX(), y = this.position.getY();
    y += this.speed;      // y-position
    this.position.setLocation(x, y);
    if(this.position.getY() >= App.getScreenHeight() - (App.getScreenHeight() * 0.186f))
        die();
}
}
