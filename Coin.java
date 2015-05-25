/*
  A coin can be clicked to generate money for the player. Each coin can have different values.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Coin extends Entity {
<<<<<<< HEAD
	
	private int value;
	
	public Coin(Point position, JLabel graphic) {
		super(position, graphic);
=======
	int value;

	public Coin(Point2D.Double x){
		super(x, null);

		//add event listener
			//on click
			//add coin value to player coins
			//die
	}

	public void die(){
		//remove from view
		//remove from current game coin list
	}

	public void update(){

>>>>>>> 569dc5af3f9ac8041890abe65f58b91c7e6cc54c
	}
}
