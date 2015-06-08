import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class TimesUp extends GameButton {
  private String name;
  private int score;

  public TimesUp(String asset, int x, int y, float w, float h){
    super(
      asset,
      null,
      null,
      null,
      x, y, w, h, false
    );
  }

  public void setName(String s){
    this.name = s;
  }

  public void setScore(int i){
    this.score = i;
  }

  public String getName(){
    return name;
  }

  public int getScore(){
    return score;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int)(img.getHeight() * 0.07f)));
    g.setColor(Color.BLACK);
    g.drawString(name, (int)(img.getWidth() * 0.5f), (int)(img.getHeight() * 0.385f));
    g.drawString(Integer.toString(score), (int)(img.getWidth() * 0.5f), (int)(img.getHeight() * 0.57f));
  }
}
