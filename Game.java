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
	private static boolean isPlaying; //if the game is paused or running
	private static boolean gameOver; //if gameOver
	private long clipTime; //to determine where the clip has paused
	private Clip clip;
	// Used to carry out the affine transform on images
  	private AffineTransform transform = new AffineTransform();

	private Random r = new Random();

	public Game(String name) {

		this.playerName = name;
		this.money = 0;
		this.foodNumber = 25;
		this.timer = 300;
		isPlaying = true; //run the game
		gameOver = false;

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e){
				// Gonna be a long code

				boolean clickedCoin = false;
				Point2D.Double pointClicked = new Point2D.Double(e.getX(), e.getY());

				if(pointClicked.getX()<100) {
					if(isPlaying){
						clipTime = clip.getMicrosecondPosition();
						clip.stop();
					} else {
						clip.setMicrosecondPosition(clipTime);
						clip.start();
					}
					isPlaying = isPlaying?false:true;
					clickedCoin = true;
				}
				else {
					clickedCoin = false;
				}
				if(isPlaying) {
					for(Coin x : coins) {
						if(x.isWithinRange(pointClicked)) {
							x.die();
							clickedCoin = true;
							System.out.println("You have " + money + " coins");
							break;
						} else {
							clickedCoin = false;
						}
					}
					if(!clickedCoin) {
						if(e.getY()>200){
							fish.add(new Fish(pointClicked));
						} else {
							foods.add(new Food(pointClicked));
						}
					}
				}


			}
			@Override
			public void mouseClicked(MouseEvent e){}

			@Override
			public void mouseExited(MouseEvent e){
				// isPlaying = false;
			}

			@Override
			public void mouseEntered(MouseEvent e){
				// isPlaying = true;
			}

			@Override
			public void mousePressed(MouseEvent e){}
		});
		Thread updateThread = new Thread () {
			@Override
			public void run() {
				while (!gameOver) {
					repaint();
					try {
						Thread.sleep(1000 / App.FRAME_RATE); // delay and yield to other threads
					} catch (InterruptedException ex) { }
					// loop thread sleep until game if the game is paused
					while(!isPlaying) {
						try {
							Thread.sleep(1000 / App.FRAME_RATE); //Pause the game
						} catch (InterruptedException ex) { }
					}
				}
			}
	    };
	    updateThread.start();  // start the thread to run updates

		//start bgm
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("assets/sounds/bgm/ingame.wav"));
			clip = AudioSystem.getClip();
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
					while(!isPlaying) {
						try {
							Thread.sleep(1000 / App.FRAME_RATE); //Pause the game
						} catch (InterruptedException ex) { }
					}
					System.out.println(timer);
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
			/*
				* Original Affine Transform to flip image. SLOW!

				if(current.getDirection()>=90 || current.getDirection()<-90){
					Flip the image vertically
					transform = AffineTransform.getScaleInstance(1, -1);
					transform.translate(0, -current.getImg().getHeight(null));
					AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
					image = op.filter(current.getImg(), null);
				}
			*/
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
			transform.rotate(Math.toRadians(current.getDirection()), current.getWidth() / 2, current.getHeight() / 2); //rotates image based on direction
    		g2d.drawImage(image, transform, null);

		}

		//coin
		for(int i = 0; i < coins.size(); i++){
			Coin current = coins.get(i);
			BufferedImage image = current.getImg();
			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
	    	g2d.drawImage(image, transform, null);

		}
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

	public int addMoney() { //Unsafe. TO UPDATE!
		return this.money++;
	}

	public boolean isPlaying() {
		return isPlaying;
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
