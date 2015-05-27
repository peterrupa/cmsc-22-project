/*
A food that gives special powers to whatever fish ate it.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class PowerupInstaMature extends Food {
    //String type (of powerup)
    public PowerupInstaMature(Point2D.Double x){
        super(x, "assets/img/food/instaMature.png");
    }

    public void die(Fish f){
        f.mature();
        f.renew();
        isAlive = false;
        App.getOngoingGame().getFoods().remove(this);
    }
}
