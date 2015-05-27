/*
	food that gives fish LBM
		//food that makes fish drop double the coin value
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class PowerupDoubleCoins extends Food {
    //String type (of powerup)
    public PowerupDoubleCoins(Point2D.Double x){
        super(x);
        // load asset
        try{
    			img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/food/doubleCoins.png")),0.02f);
    		}
    		catch(Exception e){}
    }

    public void die(Fish f){
        f.doubledCoins();
        f.renew();
        isAlive = false;
        App.getOngoingGame().getFoods().remove(this);
    }
}
