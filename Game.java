/*
  Game object stores all data specific to the whole game. This is also the view that contains all gameplay objects for the GUI.
*/
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Game extends JPanel{
	private final long SCARY_TIMESTAMP = 214 * 1000000;

  private int totalFishBought;
  private int fishDied;
  private int coinsSpent;
  private int foodBought;
  private int foodUsed;
  private int powerupInstaMatureBought;
  private int powerupInstaMatureUsed;
  private int powerupDoubleCoinsBought;
  private int powerupDoubleCoinsUsed;
  private int powerupNullHungerBought;
  private int powerupNullHungerUsed;
  private int powerupHasteBought;
  private int powerupHasteUsed;

  private int gameTime; //possible if tank empties before 5 minutes is over. Does not count pauses.

	private String playerName;
	private int foodNumber;
	private int money;
	private int timer;

	private ArrayList<Fish> fish = new ArrayList<Fish>();		//arraylist variables are the representations of the entities in the GUI
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Food> foods = new ArrayList<Food>(); // Food and poweruups go here
	private static String mouseState; //to determine what kind of food shall be instantiated when the user clicks
	private static boolean isPlaying; //if the game is paused or running
	private static boolean gameOver; //if gameOver
	private long clipTime; //to determine where the clip has paused
	private Clip clip;
	// Used to carry out the affine transform on images
	private AffineTransform transform = new AffineTransform();

	private Random r = new Random();
	private BufferedImage bgImg = null; //background image
	private BufferedImage bgImgScary = null; //background image
	private BufferedImage mainMenuBackground = null; //Main Menu image
	private BufferedImage highScoresBackground = null; //Highscores image
	private BufferedImage creditsBackground = null; //Credits image
	private BufferedImage instructionsBackground = null; //Instructions image

	private String panelMode; //Game, MainMenu, Shop,
	public static GameHistory gameHistory;


	public Game(String name) {

		gameHistory = readGameHistory(); //Load game history
		mouseState = "Food";
		this.playerName = name;
		this.timer = 300;
		isPlaying = true; //start the game paused
		gameOver = false;
		this.panelMode = "mainMenu";
		this.money = 1000;
		this.foodNumber = 1000;

		//set panel settings
		setLayout(null);
		setSize(new Dimension(App.getScreenWidth(), App.getScreenHeight()));

		////////////////////////////////////////
		// GameButton test2 = new GameButton();
		// test2.setBounds(250, 500, 100, 100);
		//
		// add(test2);
		// test2.addActionListener(new ActionListener(){
		// 	@Override
		// 	public void actionPerformed(ActionEvent e){
		// 		System.out.println("FUCK YEAH");
		// 	}
		// });
		////////////////////////////////////////

		// Background Image
		try {
			bgImg = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource("assets/img/bg/bg-test.png")), 1f, 1f);
			bgImgScary = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource("assets/img/bg/bg-test2.png")), 1f, 1f);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in loading Aquaruim");
		}

		// Add list
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e){
				// Gonna be a long code
				boolean clickedCoin = false; //flagger for click priority
				Point2D.Double pointClicked = new Point2D.Double(e.getX(), e.getY());

				// Panel Mode checker
				if(isPlaying && panelMode == "game") { //clicks will only register if game is not paused and if is in game panel
					for(Coin x : coins) {
						// checks each coin in the coin array for first instance where the click is within bounds
						if(x.isWithinRange(pointClicked)) {
							x.die(); // Auto increments money variable of player
							clickedCoin = true;
							Utilities.playSFX("assets/sounds/sfx/coin_click.wav");
							// System.out.println("You have " + money + " coins");
							break;
						} else {
							clickedCoin = false;
						}
					}
					if(!clickedCoin) {
						if(foodNumber > 0) {
							switch(mouseState) {
								case "Food": {
									foods.add(new Food(pointClicked));
								} break;

								case "PowerupInstaMature": {
									foods.add(new PowerupInstaMature(pointClicked));
								} break;

								case "PowerupNullHunger": {
									foods.add(new PowerupNullHunger(pointClicked));
								} break;

								case "PowerupDoubleCoins": {
									foods.add(new PowerupDoubleCoins(pointClicked));
								} break;
							}
							foodNumber-=1;
						}
					}
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

		// TEMPORARY, PARA LANG MAKAPAGLAGAY TAYO NG FOOD
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "newFish");
		this.getActionMap().put("newFish", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(money >= 20) {
					fish.add(new Fish(false));
					money-=20;
				}
			}
		});

		// Pause
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "pause");
		this.getActionMap().put("pause", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
					gamePause();
			}
		});

		// Food
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"), "Food");
		this.getActionMap().put("Food", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				mouseState = "Food";
			}
		});

		// PowerupInstaMature
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("X"), "PowerupInstaMature");
		this.getActionMap().put("PowerupInstaMature", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				mouseState = "PowerupInstaMature";
			}
		});

		// PowerupNullHunger
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "PowerupNullHunger");
		this.getActionMap().put("PowerupNullHunger", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				mouseState = "PowerupNullHunger";
			}
		});

		// PowerupDoubleCoins
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"), "PowerupDoubleCoins");
		this.getActionMap().put("PowerupDoubleCoins", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				mouseState = "PowerupDoubleCoins";
			}
		});

		// Convert MainMenu fish to Game fish (they start to hunger and spawn coins)
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "startGame");
		this.getActionMap().put("startGame", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				startGame();
			}
		});

		Thread updateThread = new Thread () {
			@Override
			public void run() { //Main game loop
				while (!gameOver) { //While not yet game over
					repaint();
					try {
						Thread.sleep(1000 / App.FRAME_RATE);
					} catch (InterruptedException ex) { }
					// loop thread sleep until game if the game is paused
					while(!isPlaying) { // if paused, sleep indefinitely
						try {
							Thread.sleep(1000 / App.FRAME_RATE); //Pause the game
						} catch (InterruptedException ex) { }
					}
				}
				saveGame();
			}
	    };
	    updateThread.start();  // start the thread to run updates

		//start bgm
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("assets/sounds/bgm/ingame.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			// gamePause();
			// clip.loop(Clip.LOOP_CONTINUOUSLY); //IF NEEDED LOOP, MOST LIKELY FOR MENU BGM
		}
		catch(Exception e){}

		Thread timerThread = new Thread () { //thread for timer for game triggers and events (bgm triggers, etc)
			@Override
			public void run() {
				while (!gameOver) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) { }
					timer--;
					// System.out.println(timer);
					if(timer==0)
						gameOver = true;  //END GAME AFTER 5 MINS
					while(!isPlaying) {
						try {
							Thread.sleep(1000 / App.FRAME_RATE); //Pause the game
						} catch (InterruptedException ex) { }
					}
				}
			}
		};
		timerThread.start();  // start the thread to run updates
	}

	public void endGame() {
		// Stops the Running game by flagging gameOver variable
		// End game when tank is empty or if timer runs out.
		gameOver = true;
	}

	public void saveGame() {

		gameHistory.addPlayer(new Player("Matthew Marcos69" , this.money));

		try{
			FileOutputStream fos = new FileOutputStream("gameHistory.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(gameHistory);
			oos.close();
			System.out.println("Game saved successfully!");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException Line 261 Game.java");
		} catch (IOException e) {
			System.out.println("IOException Line 263 Game.java");
		}

		return;
	}

	private GameHistory readGameHistory() {
		// Read game history.ser. else return new instance
		try{
			FileInputStream fis = new FileInputStream("gameHistory.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			GameHistory temp = (GameHistory)ois.readObject();
			ois.close();
			temp.printPlayers();
			return temp;
		}catch (FileNotFoundException e) {
			System.out.println("Game History not found. A new one will be created when you exit");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new GameHistory();
	}
	public void gamePause() {
		if(isPlaying){
			clipTime = clip.getMicrosecondPosition();
			clip.stop();
		} else {
			clip.setMicrosecondPosition(clipTime);
			clip.start();
		}
		isPlaying = isPlaying?false:true;
	}

	/** Custom painting codes on this JPanel */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		super.paintComponent(g);  // paint background

		transform.setToIdentity();
		g2d.drawImage(clip.getMicrosecondPosition() < SCARY_TIMESTAMP? bgImg: bgImgScary, transform, null);

		//paint food
		for(int i = 0; i < foods.size(); i++){
			Food current = foods.get(i);
			BufferedImage image = current.getImg();

			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
			g2d.drawImage(image, transform, null);
		}
		//paint fish
		for(int i = 0; i < fish.size(); i++){
			Fish current = fish.get(i);
			BufferedImage image = current.getImg();
			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
			transform.rotate(Math.toRadians(current.getDirection()), current.getWidth() / 2, current.getHeight() / 2); //rotates image based on direction
			g2d.drawImage(image, transform, null);

			// check if fish is dying, apply green tint
			if(current.getLifespan() <= 8){
				g2d.drawImage(createGreenVersion(image, redTintModifier(current.getLifespan())), transform, null);
			}
		}

		// paint coin
		for(int i = 0; i < coins.size(); i++){
			Coin current = coins.get(i);
			BufferedImage image = current.getImg();
			transform.setToIdentity();
			transform.translate(current.getPosition().getX() - current.getWidth() / 2, current.getPosition().getY() - current.getHeight() / 2);
			g2d.drawImage(image, transform, null);
		}

		super.paintComponents(g);  // paint background

		g2d.dispose();
	}

	public void startGame() {
		System.out.println("Starting game...");

		panelMode = "game";

		for(Fish x : fish) {
			x.convertToGameFish();
			System.out.println("Converted " + x + " to game Fish");
		}
	}

	public void start(){
		for(int i=0; i<2; i++) {
			fish.add(new Fish(new Point2D.Double(r.nextInt(App.getScreenWidth()/2)+200, r.nextInt(App.getScreenHeight()/2)+200), true));
		}
	}

	private BufferedImage createGreenVersion(BufferedImage image, float f){
		// create red image
		BufferedImage greenVersion = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d2 = (Graphics2D) greenVersion.getGraphics();
		g2d2.setColor(new Color(0, 65, 11));
		g2d2.fillRect(0, 0, image.getWidth(), image.getHeight());

		// paint original with composite
		g2d2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, f));
		g2d2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);

		return greenVersion;
	}

	private float redTintModifier(int x){
		return 0.3f + (8 - x) * 0.07f;
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

	public int addMoney(int value) {
		return this.money += value;
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

	// public Point2D.Double setPoint(double x, double y) {
	// 	return new Point(100*x*App.getScreenWidth() , 100*y*App.getScreenHeight());
	// }
}
