/*
  An entity is an object in the game view (excluding buttons/labels) that occupies space and holds game logic.
*/
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.Random;

@SuppressWarnings("serial") //make the linter/compiler shut up
public abstract class Entity {
	protected Random r = new Random();

	// Position of the Entity on the screen
	protected Point2D.Double position;
	protected BufferedImage img = null;
	protected double imgWidth;
	protected double imgHeight;
	protected double direction;
	protected double speed;
	protected boolean isAlive;

	protected Thread updateThread;
	// protected Point2D.Double position2 = new Point2D.Double(r.nextInt(1300), r.nextInt(700));

	public Entity(Point2D.Double x, String imageFileLocation){
		// Instantiate with current location and image
		// this.image = y;
		this.position = x;
		this.isAlive = true;
		//this should not be here, let the subclass do this
	    try{
	    	img = ImageIO.read(getClass().getClassLoader().getResource(imageFileLocation));
	    }
	    catch(Exception e){}
	}

	public Point2D.Double getPosition(){
		// Returns the current position of the Entity
		return this.position;
	}

	public double getWidth(){
		return imgWidth;
	}

	public double getHeight(){
		return imgHeight;
	}

	public double getDirection(){
		return direction;
	}

	public BufferedImage getImg(){
		return img;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	protected void startThread() {
		updateThread = new Thread () {
			@Override
			public void run() {
				while (isAlive) {
					update();
					try {
					  Thread.sleep(1000 / App.FRAME_RATE); // delay and yield to other threads
					} catch (InterruptedException ex) { }
					// Pause updating
					while(!App.getOngoingGame().isPlaying()) {
						try {
							Thread.sleep(1000 / App.FRAME_RATE); //Pause the game
						} catch (InterruptedException ex) { }
					}
				}
			}
		};
	    updateThread.start();  // start the thread to run updates

	}

	public abstract void die(); //WHen fish dies or food hits ground or coin is clicked
	public abstract void update(); //updates location and
}
