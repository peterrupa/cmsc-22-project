/*
	Concentrated food that makes a fish full for a period of time.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;


@SuppressWarnings("serial") //make the linter/compiler shut up
public class PowerupNullHunger extends Food {
    //String type (of powerup)
    public PowerupNullHunger(Point2D.Double x){
        super(x);
        // load asset
    		try{
    			img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/food/pauseHunger.png")),0.04f);
    		}
    		catch(Exception e){}
    }

    public void die(Fish f){
        f.nullHunger();
        f.renew();
        isAlive = false;
        App.getOngoingGame().getFoods().remove(this);
    }
}
