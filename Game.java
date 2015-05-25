/*
  Game object stores all data specific to the whole game. This is also the view that contains all gameplay objects for the GUI.
*/
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Game extends JPanel{

	private String playerName;
	private int foodNumber;
	private int money;
	private int timer;
	private ArrayList<Fish> fish = new ArrayList<Fish>();		//arraylist variables are the representations of the entities in the GUI
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Food> foods = new ArrayList<Food>();

	// Used to carry out the affine transform on images
  private AffineTransform transform = new AffineTransform();

	private Random r = new Random();

	public Game(String name) {
		this.playerName = name;
		this.money = 0;
		this.foodNumber = 25;
		this.timer = 300;

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e){
				// Gonna be a long code
				Point2D.Double pointClicked = new Point2D.Double(e.getX(), e.getY());
				if(e.getY()>200){
					fish.add(new Fish(pointClicked));
				} else {
					foods.add(new Food(pointClicked));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e){}

			@Override
			public void mouseExited(MouseEvent e){}

			@Override
			public void mouseEntered(MouseEvent e){}

			@Override
			public void mousePressed(MouseEvent e){}
		});

		Thread updateThread = new Thread () {
       @Override
       public void run() {
          while (true) {
						repaint();

            try {
              Thread.sleep(1000 / App.FRAME_RATE); // delay and yield to other threads
            } catch (InterruptedException ex) { }
          }
       }
    };
    updateThread.start();  // start the thread to run updates

		//start bgm
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("assets/sounds/bgm/ingame.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			// clip.loop(Clip.LOOP_CONTINUOUSLY); //IF NEEDED LOOP, MOST LIKELY FOR MENU BGM
		}
		catch(Exception e){}

		Thread timerThread = new Thread () {
       @Override
       public void run() {
          while (true) {
			try {
              Thread.sleep(1000); // delay and yield to other threads
            } catch (InterruptedException ex) { }

				timer--;
				// System.out.println(timer);
          }
       }
    };
    timerThread.start();  // start the thread to run updates
	}

	/** Custom painting codes on this JPanel */
	@Override
	public void paintComponent(Graphics g) {
	super.paintComponent(g);  // paint background
	setBackground(timer > 88? Color.GREEN: Color.RED);
	Graphics2D g2d = (Graphics2D) g;


		//food
		for(int i = 0; i < foods.size(); i++){
			Food current = foods.get(i);
			// Check if food is dead
			if(!current.isAlive()) {
				foods.remove(current);
				continue;
			}
			BufferedImage image = current.getImg();
			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
	    	g2d.drawImage(image, transform, null);

			}

		//fish
		for(int i = 0; i < fish.size(); i++){
			Fish current = fish.get(i);
			BufferedImage image = current.getImg();
			transform.setToIdentity();
			if(current.getDirection()>=90 || current.getDirection()<-90){
				// Flip the image vertically
				transform = AffineTransform.getScaleInstance(1, -1);
				transform.translate(0, -current.getImg().getHeight(null));
				AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(current.getImg(), null);
			}

			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);

			transform.rotate(Math.toRadians(current.getDirection()), current.getWidth() / 2, current.getHeight() / 2);

    	g2d.drawImage(image, transform, null);

		}

		//coin

	}

	public ArrayList<Food> getFoods() {
		return this.foods;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public int getFoodNumber() {
		return this.foodNumber;
	}

	public int getMoney() {
		return this.money;
	}

	public ArrayList<Fish> getFish() {
		return this.fish;
	}

	public ArrayList<Coin> getCoins() {
		return this.coins;
	}

	public ArrayList<Food> getFood() {
		return this.foods;
	}
}
