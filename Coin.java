/*
A coin can be clicked to generate money for the player. Each coin can have different values.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.imageio.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Coin extends Entity {

	int value;

	public boolean isWithinRange(Point2D.Double testPoint) {
		/*
		checks if testPoint is within the vicinity of the coin's clickbox
		*/
		if(testPoint.getX() >= this.position.getX()-imgWidth/2 && testPoint.getX()<= this.position.getX() + imgWidth/2 &&
		testPoint.getY() >= this.position.getY()-imgHeight/2 && testPoint.getY()<= this.position.getY() + imgHeight/2) {
			return true;
		}
		else {
			return false;
		}


	}

	public Coin(Point2D.Double x, int value) {
		super(x);
		this.speed = 0.5;
		this.value = value;

		// load asset
		try{
			switch(value) {
				case 1:
				case 2: {
					img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/coins/bronzeMini4.png")),0.04f);
				} break;
				case 3:
				case 6: {
					img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/coins/silverMini4.png")),0.04f);
				} break;
				case 5:
				case 10: {
					img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource("assets/img/coins/goldMini4.png")),0.04f);
				} break;
			}

		}
		catch(Exception e){}

		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		startThread();
	}

	public void die(){
		//remove from view
		//remove from current game coin list
		isAlive = false;
		App.getOngoingGame().getCoins().remove(this);
		App.getOngoingGame().addMoney(value);
	}

	public void update(){
		double x = this.position.getX(), y = this.position.getY();
		if(this.position.getY() >= App.getScreenHeight() - (App.getScreenHeight() * 0.186f)) {
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
