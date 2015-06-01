import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class HighScores extends GameButton {
  ArrayList<Player> topFive;

  public HighScores(String asset, int x, int y, float w, float h, ArrayList<Player> topFive){
    super(
      asset,
      null,
      null,
      null,
      x, y, w, h, false
    );

    this.topFive = topFive;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int)(img.getHeight() * 0.06f)));
    g.setColor(Color.BLACK);

    for(int i = 0; i < topFive.size(); i++){
      Player current = topFive.get(i);
      g.drawString((i+1)+".", (int)(img.getWidth() * 0.2f), (int)(img.getHeight() * (0.32f + i * 0.10f)));
      g.drawString(current.getName(), (int)(img.getWidth() * 0.35f), (int)(img.getHeight() * (0.32f + i * 0.10f)));
      g.drawString(Integer.toString(current.getScore()), (int)(img.getWidth() * 0.7f), (int)(img.getHeight() * (0.32f + i * 0.10f)));
    }

  }
}
