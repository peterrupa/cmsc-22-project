/*
  A coin can be clicked to generate money for the player. Each coin can have different values.
*/

public class Coin extends Entity {
	
	private int value;
	
	public Coin(Point position, JLabel graphic) {
		super(position, graphic);
	}
}
