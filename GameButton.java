import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial") //make the linter/compiler shut up
public class GameButton extends JButton{
	BufferedImage img, img_hover, img_current, img_disabled, img_pressed;
	boolean disabled = false;

	public GameButton(String asset, String asset_hover, String asset_disabled, String asset_pressed, int x, int y, float w, float h, boolean square){
		try{
			if(square){
				img = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource(asset)), w);
				if(asset_hover != null){
					img_hover = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource(asset_hover)), w);
				}
				if(asset_disabled != null){
					img_disabled = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource(asset_disabled)), w);
				}
				if(asset_pressed != null){
					img_pressed = Utilities.flexImageSquare(ImageIO.read(getClass().getClassLoader().getResource(asset_pressed)), w);
				}
			}
			else{
				img = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource(asset)), w, h);
				if(asset_hover != null){
					img_hover = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource(asset_hover)), w, h);
				}
				if(asset_disabled != null){
					img_disabled = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource(asset_disabled)), w, h);
				}
				if(asset_pressed != null){
					img_pressed = Utilities.flexImage(ImageIO.read(getClass().getClassLoader().getResource(asset_pressed)), w, h);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		img_current = img;

		setBorder(null);
		setBorderPainted(false);
		setOpaque(false);
		setFocusPainted(false);

		//set proper coordinates

		int width = (int)(App.getScreenWidth() * w),
				height = (int)(App.getScreenHeight() * h);

		x = x - (img.getWidth() / 2);
		y = y - (img.getHeight() / 2);

		if(square){
			setBounds(x, y, width, width);
		}
		else{
			setBounds(x, y, width, height);
		}

		addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(MouseEvent e){
				if(!disabled && img_hover != null){
					img_current = img_hover;
				}
			}

			@Override
			public void mouseExited(MouseEvent e){
				if(!disabled){
					img_current = img;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e){
				if(!disabled && img_hover != null){
					img_current = img_hover;
				}
			}

			@Override
			public void mousePressed(MouseEvent e){
				if(!disabled && img_pressed != null){
					img_current = img_pressed;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e){}
		});
	}

	public void paintComponent(Graphics g){
		g.drawImage(img_current, 0, 0, null);
	}

	public void setEnabled(){
		img_current = img;
		disabled = false;
		System.out.println("NOT HAPPEN");
	}

	public void setDisabled(){
		img_current = img_disabled;
		disabled = true;
	}

	public boolean isEnabled(){
		return !disabled;
	}
}
