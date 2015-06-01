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
	private int pauseHungerNumber;
	private int doubleCoinsNumber;
	private int instaMatureNumber;
	private int hasteNumber;
	private int money;
	private int timer;

	private ArrayList<Fish> fish = new ArrayList<Fish>();		//arraylist variables are the representations of the entities in the GUI
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Food> foods = new ArrayList<Food>(); // Food and poweruups go here
	private static String mouseState; //to determine what kind of food shall be instantiated when the user clicks
	private static boolean isPlaying; //if the game is paused or running
	private static boolean gameOver; //if gameOver
	private long clipTime; //to determine where the ingameClip has paused
	private Clip menuClip;
	private Clip ingameClip;
	// Used to carry out the affine transform on images
	private AffineTransform transform = new AffineTransform();

	private Random r = new Random();
	private BufferedImage bgImg = null; //background image
	private BufferedImage bgImgScary = null; //background image

	private String panelMode; //Game, MainMenu, Shop,
	public static GameHistory gameHistory;

	//store all buttons here
	GameButton logo = null;
	GameButton menuLogo = null;
	GameButton foodButton = null;
	GameButton fishButton = null;
	GameButton pauseHungerButton = null;
	GameButton doubleCoinsButton = null;
	GameButton instaMatureButton = null;
	GameButton hasteButton = null;
	GameButton muteButton = null;
	Counter coinCounter = null;
	Counter foodCounter = null;
	Counter pauseHungerCounter = null;
	Counter instaMatureCounter = null;
	Counter doubleCoinsCounter = null;
	Counter hasteCounter = null;


	GameButton playButton = null;
	GameButton instructionsButton = null;
	GameButton highScoresButton = null;
	GameButton creditsButton = null;
	GameButton exitButton = null;
	GameButton instructions = null;
	GameButton credits = null;
	GameButton mainMenuButton = null;

	Thread timerThread;

	public Game(String name) {

		gameHistory = readGameHistory(); //Load game history
		mouseState = "Food";
		this.playerName = name;
		this.timer = 300;
		isPlaying = true; //start the game paused
		gameOver = false;
		this.panelMode = "mainMenu";
		this.money = 0;
		this.foodNumber = 25;
		this.pauseHungerNumber = 0;
		this.instaMatureNumber = 0;
		this.doubleCoinsNumber = 0;
		this.hasteNumber = 0;

		//set panel settings
		setLayout(null);
		setSize(new Dimension(App.getScreenWidth(), App.getScreenHeight()));

		//add all menu/ingame buttons

		//buy food
		foodButton = new GameButton(
			"assets/img/buttons/food_normal.png",
			"assets/img/buttons/food_hover.png",
			"assets/img/buttons/food_disabled.png",
			"assets/img/buttons/food_pressed.png",
			(int)(App.getScreenWidth() * 0.2f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		foodButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(foodButton.isEnabled()){
					buy("food");
				}
			}
		});

		add(foodButton);
		foodButton.setVisible(false);

		//buy fish
		fishButton = new GameButton(
			"assets/img/buttons/fish_normal.png",
			"assets/img/buttons/fish_hover.png",
			"assets/img/buttons/fish_disabled.png",
			"assets/img/buttons/fish_pressed.png",
			(int)(App.getScreenWidth() * 0.3f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		fishButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(fishButton.isEnabled()){
					buy("fish");
				}
			}
		});

		add(fishButton);
		fishButton.setVisible(false);

		//pause hunger button
		pauseHungerButton = new GameButton(
			"assets/img/buttons/pause_hunger_normal.png",
			"assets/img/buttons/pause_hunger_hover.png",
			"assets/img/buttons/pause_hunger_disabled.png",
			"assets/img/buttons/pause_hunger_pressed.png",
			(int)(App.getScreenWidth() * 0.45f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		pauseHungerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(pauseHungerButton.isEnabled()){
					buy("pauseHunger");
				}
			}
		});

		add(pauseHungerButton);
		pauseHungerButton.setVisible(false);

		//double coin button
		doubleCoinsButton = new GameButton(
			"assets/img/buttons/double_coins_normal.png",
			"assets/img/buttons/double_coins_hover.png",
			"assets/img/buttons/double_coins_disabled.png",
			"assets/img/buttons/double_coins_pressed.png",
			(int)(App.getScreenWidth() * 0.55f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		doubleCoinsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(doubleCoinsButton.isEnabled()){
					buy("doubleCoins");
				}
			}
		});

		add(doubleCoinsButton);
		doubleCoinsButton.setVisible(false);

		//insta mature button
		instaMatureButton = new GameButton(
			"assets/img/buttons/insta_mature_normal.png",
			"assets/img/buttons/insta_mature_hover.png",
			"assets/img/buttons/insta_mature_disabled.png",
			"assets/img/buttons/insta_mature_pressed.png",
			(int)(App.getScreenWidth() * 0.65f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		instaMatureButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(instaMatureButton.isEnabled()){
					buy("instaMature");
				}
			}
		});

		add(instaMatureButton);
		instaMatureButton.setVisible(false);

		//haste
		hasteButton = new GameButton(
			"assets/img/buttons/haste_normal.png",
			"assets/img/buttons/haste_hover.png",
			"assets/img/buttons/haste_disabled.png",
			"assets/img/buttons/haste_pressed.png",
			(int)(App.getScreenWidth() * 0.75f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.08f, 0.08f, true
		);

		hasteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(hasteButton.isEnabled()){
					buy("haste");
				}
			}
		});

		add(hasteButton);
		hasteButton.setVisible(false);

		//mute
		muteButton = new GameButton(
			"assets/img/buttons/unmute_normal.png",
			"assets/img/buttons/unmute_hover.png",
			null,
			"assets/img/buttons/unmute_pressed.png",
			App.getScreenWidth() - (int)(App.getScreenWidth() * 0.07f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.065f, 0.065f, true
		);

		muteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("mute");
			}
		});

		add(muteButton);

		//logo
		logo = new GameButton(
			"assets/img/logo/logo.png",
			null,
			null,
			null,
			(int)(App.getScreenWidth() * 0.07f),
			App.getScreenHeight() - (int)(App.getScreenHeight() * 0.1f),
			0.09f, 0.09f, true
		);

		add(logo);
		logo.setVisible(false);

		//counters
		coinCounter = new Counter(
			"assets/img/counters/coin_normal.png",
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.06f),
			0.10f, 0.08f, false
		);

		coinCounter.setVisible(false);
		add(coinCounter);

		foodCounter = new Counter(
			"assets/img/counters/food_normal.png",
			(int)(App.getScreenWidth() * 0.065f),
			(int)(App.getScreenHeight() * 0.3f),
			0.10f, 0.08f, false
		);

		foodCounter.setVisible(false);

		pauseHungerCounter = new Counter(
			"assets/img/counters/pause_hunger_normal.png",
			(int)(App.getScreenWidth() * 0.065f),
			(int)(App.getScreenHeight() * 0.4f),
			0.10f, 0.08f, false
		);

		pauseHungerCounter.setVisible(false);

		instaMatureCounter = new Counter(
			"assets/img/counters/insta_mature_normal.png",
			(int)(App.getScreenWidth() * 0.065f),
			(int)(App.getScreenHeight() * 0.5f),
			0.10f, 0.08f, false
		);

		instaMatureCounter.setVisible(false);

		doubleCoinsCounter = new Counter(
			"assets/img/counters/double_coins_normal.png",
			(int)(App.getScreenWidth() * 0.065f),
			(int)(App.getScreenHeight() * 0.6f),
			0.10f, 0.08f, false
		);

		doubleCoinsCounter.setVisible(false);

		hasteCounter = new Counter(
			"assets/img/counters/haste_normal.png",
			(int)(App.getScreenWidth() * 0.065f),
			(int)(App.getScreenHeight() * 0.7f),
			0.10f, 0.08f, false
		);

		hasteCounter.setVisible(false);

		add(foodCounter);
		add(pauseHungerCounter);
		add(instaMatureCounter);
		add(doubleCoinsCounter);
		add(hasteCounter);

		//MENU ELEMENTS

		//shared main menu button
		mainMenuButton = new GameButton(
			"assets/img/buttons/main_menu_normal.png",
			"assets/img/buttons/main_menu_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.63f),
			(int)(App.getScreenHeight() * 0.75f),
			0.20f, 0.09f, false
		);

		mainMenuButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				setPanel("mainMenu");
			}
		});

		mainMenuButton.setVisible(false);

		add(mainMenuButton);

		//instructions
		instructions = new GameButton(
			"assets/img/screen/instructions.png",
			null,
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.5f),
			0.57f, 0.68f, false
		);

		instructions.setVisible(false);
		add(instructions);

		//credits
		credits = new GameButton(
			"assets/img/screen/credits.png",
			null,
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.5f),
			0.57f, 0.68f, false
		);

		credits.setVisible(false);
		add(credits);

		//logo
		menuLogo = new GameButton(
			"assets/img/logo/menu_logo.png",
			null,
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.15f),
			0.45f, 0.17f, false
		);

		add(menuLogo);

		//play button
		playButton = new GameButton(
			"assets/img/buttons/menu_play_normal.png",
			"assets/img/buttons/menu_play_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.35f),
			0.20f, 0.09f, false
		);

		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "mainMenu"){
					System.out.println("PLAY");
				}
			}
		});

		add(playButton);

		//INSTRUCTIONS button
		instructionsButton = new GameButton(
			"assets/img/buttons/menu_instructions_normal.png",
			"assets/img/buttons/menu_instructions_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.45f),
			0.20f, 0.09f, false
		);

		instructionsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "mainMenu"){
					setPanel("instructions");
				}
			}
		});

		add(instructionsButton);

		//high scores button
		highScoresButton = new GameButton(
			"assets/img/buttons/menu_high_scores_normal.png",
			"assets/img/buttons/menu_high_scores_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.55f),
			0.20f, 0.09f, false
		);

		highScoresButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "mainMenu"){
					System.out.println("HIGH SCORES");
				}
			}
		});

		add(highScoresButton);

		//credits button
		creditsButton = new GameButton(
			"assets/img/buttons/menu_credits_normal.png",
			"assets/img/buttons/menu_credits_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.65f),
			0.20f, 0.09f, false
		);

		creditsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "mainMenu"){
					setPanel("credits");
				}
			}
		});

		add(creditsButton);

		//exit button
		exitButton = new GameButton(
			"assets/img/buttons/menu_exit_normal.png",
			"assets/img/buttons/menu_exit_hover.png",
			null,
			null,
			(int)(App.getScreenWidth() * 0.5f),
			(int)(App.getScreenHeight() * 0.75f),
			0.20f, 0.09f, false
		);

		exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "mainMenu"){
					System.exit(0);
				}
			}
		});

		add(exitButton);

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
						switch(mouseState) {
							case "Food": {
								if(foodNumber > 0){
									foods.add(new Food(pointClicked));
									foodNumber--;
								}
							} break;

							case "PowerupInstaMature": {
								if(instaMatureNumber > 0){
									foods.add(new PowerupInstaMature(pointClicked));
									instaMatureNumber--;
								}
							} break;

							case "PowerupNullHunger": {
								if(pauseHungerNumber > 0){
									foods.add(new PowerupNullHunger(pointClicked));
									pauseHungerNumber--;
								}
							} break;

							case "PowerupDoubleCoins": {
								if(doubleCoinsNumber > 0){
									foods.add(new PowerupDoubleCoins(pointClicked));
									doubleCoinsNumber--;
								}
							} break;

							case "PowerupHaste": {
								if(hasteNumber > 0){
									foods.add(new PowerupHaste(pointClicked));
									hasteNumber--;
								}
							} break;
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

		// Pause
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "pause");
		this.getActionMap().put("pause", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(panelMode == "game"){
					gamePause();
				}
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

		// Poweruphaste
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("B"), "PowerupHaste");
		this.getActionMap().put("PowerupHaste", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				mouseState = "PowerupHaste";
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

		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("assets/sounds/bgm/menu.wav"));
			menuClip = AudioSystem.getClip();
			menuClip.open(audioInputStream);
			menuClip.start();
			menuClip.loop(Clip.LOOP_CONTINUOUSLY); //IF NEEDED LOOP, MOST LIKELY FOR MENU BGM
		}
		catch(Exception e){}

		Thread updateThread = new Thread () {
			@Override
			public void run() { //Main game loop
				while (!gameOver) { //While not yet game over
					update();
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

		timerThread = new Thread () { //thread for timer for game triggers and events (bgm triggers, etc)
			@Override
			public void run() {
				while (!gameOver) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) { }
					timer--;
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
	}

	public void buy(String s){
		switch(s){
			case "fish":
				if(money >= 20) {
					fish.add(new Fish(false));
					money-=20;
				}
				break;
			case "food":
				if(money >= 25) {
					foodNumber += 25;
					money-=25;
				}
				break;
			case "pauseHunger":
				if(money >= 25) {
					pauseHungerNumber += 5;
					money-=25;
				}
				break;
			case "instaMature":
				if(money >= 25) {
					instaMatureNumber += 5;
					money-=25;
				}
				break;
			case "doubleCoins":
				if(money >= 25) {
					doubleCoinsNumber += 5;
					money-=25;
				}
				break;
			case "haste":
				if(money >= 25) {
					hasteNumber += 5;
					money-=25;
				}
				break;
		}
		Utilities.playSFX("assets/sounds/sfx/buy.wav");
	}

	public void setPanel(String panel){
		switch(panel){
			case "mainMenu":
				//add menu elements here
				this.panelMode = "mainMenu";

				logo.setVisible(false);
				foodButton.setVisible(false);
				fishButton.setVisible(false);
				pauseHungerButton.setVisible(false);
				instaMatureButton.setVisible(false);
				doubleCoinsButton.setVisible(false);
				hasteButton.setVisible(false);

				coinCounter.setVisible(false);
				foodCounter.setVisible(false);
				pauseHungerCounter.setVisible(false);
				instaMatureCounter.setVisible(false);
				doubleCoinsCounter.setVisible(false);
				hasteCounter.setVisible(false);

				menuLogo.setVisible(true);
				playButton.setVisible(true);
				instructionsButton.setVisible(true);
				highScoresButton.setVisible(true);
				creditsButton.setVisible(true);
				exitButton.setVisible(true);

				instructions.setVisible(false);
				credits.setVisible(false);
				mainMenuButton.setVisible(false);

				break;
			case "game":
				//add ingame elements here
				this.panelMode = "game";
				logo.setVisible(true);
				foodButton.setVisible(true);
				fishButton.setVisible(true);
				pauseHungerButton.setVisible(true);
				instaMatureButton.setVisible(true);
				doubleCoinsButton.setVisible(true);
				hasteButton.setVisible(true);

				coinCounter.setVisible(true);
				foodCounter.setVisible(true);
				pauseHungerCounter.setVisible(true);
				instaMatureCounter.setVisible(true);
				doubleCoinsCounter.setVisible(true);
				hasteCounter.setVisible(true);

				menuLogo.setVisible(false);
				playButton.setVisible(false);
				instructionsButton.setVisible(false);
				highScoresButton.setVisible(false);
				creditsButton.setVisible(false);
				exitButton.setVisible(false);

				break;
			case "instructions":
				this.panelMode = "instructions";
				instructions.setVisible(true);
				mainMenuButton.setVisible(true);
				break;
			case "credits":
				this.panelMode = "credits";
				credits.setVisible(true);
				mainMenuButton.setVisible(true);
				break;
		}
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

	private void update(){
		//update money
		coinCounter.setCount(money);
		foodCounter.setCount(foodNumber);
		pauseHungerCounter.setCount(pauseHungerNumber);
		instaMatureCounter.setCount(instaMatureNumber);
		doubleCoinsCounter.setCount(doubleCoinsNumber);
		hasteCounter.setCount(hasteNumber);

		//shop states
		if(money < 20){
			if(fishButton.isEnabled()){
				fishButton.setDisabled();
			}
		}
		else{
			if(!fishButton.isEnabled()){
				fishButton.setEnabled();
			}
		}
		if(money < 25){
			if(foodButton.isEnabled()){
				foodButton.setDisabled();
			}
			if(pauseHungerButton.isEnabled()){
				pauseHungerButton.setDisabled();
			}
			if(instaMatureButton.isEnabled()){
				instaMatureButton.setDisabled();
			}
			if(doubleCoinsButton.isEnabled()){
				doubleCoinsButton.setDisabled();
			}
			if(hasteButton.isEnabled()){
				hasteButton.setDisabled();
			}
		}
		else{
			if(!foodButton.isEnabled()){
				foodButton.setEnabled();
			}
			if(!pauseHungerButton.isEnabled()){
				pauseHungerButton.setEnabled();
			}
			if(!instaMatureButton.isEnabled()){
				instaMatureButton.setEnabled();
			}
			if(!doubleCoinsButton.isEnabled()){
				doubleCoinsButton.setEnabled();
			}
			if(!hasteButton.isEnabled()){
				hasteButton.setEnabled();
			}
		}
	}

	private GameHistory readGameHistory() {
		// Read game history.ser. else return new instance
		try{
			FileInputStream fis = new FileInputStream("gameHistory.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			GameHistory temp = (GameHistory)ois.readObject();
			ois.close();
			temp.getTopFive();
			// temp.printPlayers();
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
			clipTime = ingameClip.getMicrosecondPosition();
			ingameClip.stop();
		} else {
			ingameClip.setMicrosecondPosition(clipTime);
			ingameClip.start();
		}
		isPlaying = isPlaying?false:true;
	}

	/** Custom painting codes on this JPanel */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		super.paintComponent(g);  // paint background

		transform.setToIdentity();

		if(ingameClip == null || ingameClip.getMicrosecondPosition() < SCARY_TIMESTAMP){
			g2d.drawImage(bgImg, transform, null);
		}
		else{
			g2d.drawImage(bgImgScary, transform, null);
		}

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

		super.paintComponents(g);

		g2d.dispose();
	}

	public void startGame() {
		System.out.println("Starting game...");

		setPanel("game");

		for(Fish x : fish) {
			x.convertToGameFish();
			System.out.println("Converted " + x + " to game Fish");
		}

		Utilities.playSFX("assets/sounds/sfx/start_game.wav");

		//start bgm
		try{
			menuClip.stop();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("assets/sounds/bgm/ingame.wav"));
			ingameClip = AudioSystem.getClip();
			ingameClip.open(audioInputStream);
			ingameClip.start();
		}
		catch(Exception e){}

		timerThread.start();  // start the thread to run updates
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
}
