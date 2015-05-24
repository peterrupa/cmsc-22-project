/*
    The main application object. Stores the current game session, game history, and frames.
*/

import java.awt.*;
import javax.swing.*;

public class App {
	//ATTRIBUTES
	public static final int FRAME_RATE = 50;

	// Current game
	private Game ongoingGame;
	private GameHistory history;

	public App() throws Exception{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
					//this will serve as the main application
					JFrame frame = new JFrame();
					//set frame settings
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setFocusable(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setUndecorated(true);

					frame.pack();

					Container cp = frame.getContentPane();

					Game gameScreen = new Game("Peter");

					cp.add(gameScreen);

					//get screen pixels
					int screenWidth = frame.getWidth();
					int screenHeight = frame.getHeight();

					//debug
					System.out.println(screenHeight);
					System.out.println(screenWidth);

					cp.setSize(new Dimension(screenWidth, screenHeight));
					gameScreen.setSize(new Dimension(screenWidth, screenHeight));

					frame.setVisible(true);
			}
		});
	}
}
