/*
    The main application object. Stores the current game session, game history, and frames.
*/

import java.awt.*;
import javax.swing.*;

public class App {
	//ATTRIBUTES
	public static final int FRAME_RATE = 50;

	// Current game
	private static Game ongoingGame;
	private GameHistory history;
	public static JFrame frame;
	private static Container cp;
	public App() throws Exception{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//this will serve as the main application
				frame = new JFrame();
				//set frame settings
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setFocusable(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setUndecorated(true);
				frame.setLayout(null);
				frame.pack();
				frame.setVisible(true);

				// Do not refactor this!
				cp = frame.getContentPane();
				cp.setSize(new Dimension(getScreenWidth(), getScreenHeight()));

				newGame();
			}
		});
	}

	public static void newGame(){
		ongoingGame = new Game();
		ongoingGame.start();

		cp.removeAll();
		cp.add(ongoingGame);
	}

	public static int getScreenWidth() {
		return frame.getWidth();
	}

	public static int getScreenHeight() {
		return frame.getHeight();
	}

	public static Game getOngoingGame() {
		return ongoingGame;
	}
}
