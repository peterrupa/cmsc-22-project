import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Counter extends GameButton {
  private int count = 0;
  private boolean isSelected;

  public Counter(String asset, int x, int y, float w, float h, boolean square){
    super(
      asset,
      null,
      null,
      null,
      x, y, w, h, square
    );
  }

  public void setCount(int x){
    count = x;
  }

  public void setSelected(boolean b){
    isSelected = b;
  }

  public boolean isSelected(){
    return isSelected;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int)(img.getHeight() * 0.6f)));
    g.setColor(Color.BLACK);
    g.drawString(Integer.toString(count), (int)(img.getWidth() * 0.6f), (int)(img.getHeight() * 0.75f));
  }
}
