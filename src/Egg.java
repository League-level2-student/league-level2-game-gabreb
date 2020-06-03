import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Egg extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int targetx;
	double mariomove;
	Egg(int x, int y, int width, int height, int targetx) {
		super(x, y, width, height);
		this.targetx = targetx;
		mariomove = ((434)/(targetx-x))/145;
		speed = 3;
		if (needImage) {
			loadImage("Egg.png");
		}
	}

	void update() {
		y += speed;
		if (mariomove < 1 && mariomove >= 0) {
			mariomove = 1.0;
		}
		else if (mariomove > -1 && mariomove <= 0) {
			mariomove = -1.0;
		}
		System.out.println(mariomove);
		x+=mariomove;
	}

	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		}
	}
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
}
