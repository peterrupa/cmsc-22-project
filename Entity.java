/*
  An entity is an object in the game view (excluding buttons/labels) that occupies space and holds game logic.
*/
 import java.awt.*;
 import javax.swing.*;
 /*
	Entity is the superclass of fish and Food


 */

@SuppressWarnings("serial") //make the linter/compiler shut up
public abstract class Entity extends JLabel {
    // Image of the entity
	protected JLabel image;

	// Position of the Entity on the screen
	protected Point position;

	public Entity(Point x, JLabel y){
		// Instantiate with current location and image
		this.image = y;
		this.position = x;
	}

	public Point getPosition(){
		// Returns the current position of the Entity
		return this.position;
	}

	public abstract void die(); //WHen fish dies or food hits ground or coin is clicked
}
