import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class Counter extends GameButton {
  private int count = 0;
  private boolean isSelected;

  BufferedImage img_selected = null;

  public Counter(String asset, String asset_selected, int x, int y, float w, float h, boolean square){
    super(
      asset,
      null,
      null,
      null,
      x, y, w, h, square
    );

    //read selected
    if(asset_selected != null){
      try{
  			if(square){
  				img_selected = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource(asset_selected)), w);
  			}
  			else{
  				img_selected = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource(asset_selected)), w, h);
  			}
  		}catch(Exception e){
  			e.printStackTrace();
  		}
    }
  }

  public void setCount(int x){
    count = x;
  }

  public void setSelected(){
    img_current = img_selected;
    isSelected = true;
  }

  public void setUnselected(){
    img_current = img;
    isSelected = false;
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
