/*
  A coin can be clicked to generate money for the player. Each coin can have different values.
*/

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Coin extends Entity {
	int value;

	public Coin(Point x, JLabel y){
		super(x, y);
	}

	public void die(){

	}

	public void setDestination(Point destination){

	}
}
