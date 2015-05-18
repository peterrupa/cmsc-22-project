/*
  An entity is an object in the game view (excluding buttons/labels) that occupies space and holds game logic.
*/
 import java.awt.*;
 import javax.swing.*;
 import java.io.Serializable;
 /*
	Entity is the superclass of fish and Food


 */

@SuppressWarnings("serial") //make the linter/compiler shut up
public abstract class Entity extends JLabel implements Runnable{

	// You put this inside t
	protected Thread t;

    // Image of the entity
	protected JLabel image;

	// Position of the Entity on the screen
	protected Point position;
	// Destination of our entity. Checked every game tick
	//Destination is the point where the fish intends to go. At idle state, a fish will go to a randomly generated point. If food is present, the fish will go to the nearest food.
	protected Point destination;

	public Entity(Point x, JLabel y){
		// Instantiate with current location and image
		this.image = y;
		this.position = x;

		// Tama ba na dito ako magstart? Think about it.
		t = new Thread(this, "");
		t.start();
	}

	public Point getPosition(){
		// Returns the current position of the Entity
		return this.position;
	}



	public abstract void run(); //What entity does
	public abstract void die(); //WHen fish dies or food hits ground or coin is clicked
	public abstract void setDestination(Point destination); //Sets destination of the entity per game tick. Different nature for fish, food, or coint

}
