/*
    The main application object. Stores the current game session, game history, and frames.
*/

import java.awt.*;
import javax.swing.*;
import java.applet.*;
import java.net.URL;

public class App {
	//ATTRIBUTES
	// Current game
	private static Game ongoingGame;
	private static GameHistory history;

	public static void main(String[] args) throws Exception{
		//this will serve as the main application
		URL ingameBG = null;
		ingameBG = new URL("assets/sounds/bgm/ingame.mp3");
		System.out.println(ingameBG.toString());

		JFrame frame = new JFrame();
		//set frame settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);

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
