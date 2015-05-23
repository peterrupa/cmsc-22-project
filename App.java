/*
    The main application object. Stores the current game session, game history, and frames.
*/

import java.awt.*;
import javax.swing.*;
import java.applet.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class App {
	//ATTRIBUTES
	// Current game
	private Game ongoingGame;
	private GameHistory history;

	public App() throws Exception{
		//this will serve as the main application
		JFrame frame = new JFrame();
		//set frame settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);

		//start bgm
		AudioInputStream audioInputStream =
        AudioSystem.getAudioInputStream(
            this.getClass().getResource("assets/sounds/bgm/ingame.wav"));
    Clip clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.start();
		// clip.loop(Clip.LOOP_CONTINUOUSLY); //IF NEEDED LOOP, MOST LIKELY FOR MENU BGM

		Container cp = frame.getContentPane();

		JPanel gameScreen = new JPanel();

		gameScreen.setSize(new Dimension(frame.getWidth(), frame.getHeight()));

		cp.add(gameScreen);

		//get screen pixels
		int screenWidth = gameScreen.getWidth();
		int screenHeight = gameScreen.getHeight();

		//debug
		System.out.println(screenWidth);
		System.out.println(screenHeight);
	}
}
