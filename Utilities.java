import java.awt.*;
import java.awt.image.*;

public class Utilities {
  public static BufferedImage flexImageSquare(BufferedImage image, float f) {
    int scaling = 100;
    int finalWidth = (int)(App.getScreenWidth() * f);
    int finalHeight = (int)(App.getScreenWidth() * f);

    BufferedImage resized = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = (Graphics2D) resized.getGraphics();

    g2d.drawImage(image, 0, 0, finalWidth, finalHeight, null);

    return resized;
  }

  public static BufferedImage flexImage(BufferedImage image, float w, float h) {
    int scaling = 100;
    int finalWidth = (int)(App.getScreenWidth() * w);
    int finalHeight = (int)(App.getScreenHeight() * h);

    BufferedImage resized = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = (Graphics2D) resized.getGraphics();

    g2d.drawImage(image, 0, 0, finalWidth, finalHeight, null);

    return resized;
  }
}
