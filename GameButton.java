import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class GameButton extends JButton{
	BufferedImage img;
	int x, y, width = 120, height = 45;

	public GameButton(){
		try{
		this.img = ImageIO.read(getClass().getClassLoader().getResource("assets/img/fish/fishOpen.png"));
		}catch(Exception e){}

		setBorder(null);
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);

	}

	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, null);
	}

	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}
}
