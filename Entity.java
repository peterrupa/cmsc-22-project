/*
  An entity is an object in the game view (excluding buttons/labels) that occupies space and holds game logic.
*/
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.Random;

@SuppressWarnings("serial") //make the linter/compiler shut up          
public abstract class Entity {
	private Random r = new Random();

	// Position of the Entity on the screen
	protected Point2D.Double position;
	protected Point2D.Double position2 = new Point2D.Double(r.nextInt(1300), r.nextInt(700));

  protected BufferedImage img = null;

  protected double imgWidth;
  protected double imgHeight;

	protected double direction;
	protected double speed = 3; //3 slow, 12 fast

	public Entity(Point2D.Double x){
		// Instantiate with current location and image
		// this.image = y;
		this.position = x;

		//this should not be here, let the subclass do this
    try{
      img = ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/test.png"));
    }
    catch(Exception e){}

    imgWidth = img.getWidth();
    imgHeight = img.getHeight();

		Thread updateThread = new Thread () {
       @Override
       public void run() {
          while (true) {
						update();
            try {
              Thread.sleep(1000 / App.FRAME_RATE); // delay and yield to other threads
            } catch (InterruptedException ex) { }
          }
       }
    };
    updateThread.start();  // start the thread to run updates
	}

  public void update() {
    double x = this.position.getX(), y = this.position.getY();
		double x2 = this.position2.getX(), y2 = this.position2.getY();

		double dx = x2 - x, dy = y2 - y;

    direction = Math.atan2(dy,dx) * 180 / Math.PI;

		//move (x,y)
    x += speed * Math.cos(Math.toRadians(direction));  // x-position
    y += speed * Math.sin(Math.toRadians(direction));  // y-position

    this.position.setLocation(x, y);

		//check if destination point
		if(x <= x2 + speed && x >= x2 - speed && y <= y2 + speed && y >= y2 - speed){
			double newPoint = r.nextInt(1300);
			double newPoint2 = r.nextInt(700);

			this.position2.setLocation(newPoint, newPoint2);
		}
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

	public abstract void die(); //WHen fish dies or food hits ground or coin is clicked
}
