/*
  A coin can be clicked to generate money for the player. Each coin can have different values.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Coin extends Entity {
	int value;

	public Coin(Point2D.Double x) {
		super(x, "assets/img/coins/coin1.png");
		this.speed = 0.5;
		this.value = 3; //value dropped by the fish depending on maturity
			//temp value //should change depending on maturity
		
		
		//add event listener
			//on click
			//add coin value to player coins
			//die
			
		imgWidth = img.getWidth();
	    imgHeight = img.getHeight();
		startThread();
	}

	public void die(){
		//remove from view
		//remove from current game coin list
		isAlive = false;
	}

	public void update(){
		double x = this.position.getX(), y = this.position.getY();
        if(this.position.getY() >= App.getScreenHeight() - (App.getScreenHeight() * 0.2)) {
        	try {
        		Thread.sleep(3000);
        	}
        	catch(InterruptedException ex) {}
			die();
        }
        else {
        	y += this.speed;  // y-position
        }
        this.position.setLocation(x, y);
	}
}
