import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Egg extends GameObject {
	public static BufferedImage image;
	public static BufferedImage bomb;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int targetx;
	int mariomove;
	Egg(int x, int y, int width, int height, int targetx) {
		super(x, y, width, height);
		this.targetx = targetx;
		mariomove = (targetx-x)/145;
		if (targetx == 1000) {
			mariomove = 0;
		}
		
		speed = 3;
		if (needImage) {
			loadImage();
		}
	}

	void update() {
		y += speed;
		x+=mariomove;
		super.update();
	}

	void draw(Graphics g) {
		if (gotImage) {
			if (GamePanel.changetobomb) {
				g.drawImage(bomb, x, y, width, height, null);
			}
			else {
			g.drawImage(image, x, y, width, height, null);
			}
		}
	}
	void loadImage() {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream("Egg.png"));
	            bomb = ImageIO.read(this.getClass().getResourceAsStream("bomb.png"));
	            
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
}
