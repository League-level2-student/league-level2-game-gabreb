import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Eagle extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int downwards;
	Audio eagleSound = new Audio("Eagle.mp3");
	boolean soundboolean = true;
	int thirdeagle = 1;
	Eagle(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 3;
		downwards = 0;
		if (needImage) {
			loadImage("Unknown.png");
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
	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} 
	}
	void update(int level) {
		x+=speed;
		y+=downwards;
		if (thirdeagle > 3 && level == 1) {
			x = -1000000;
		}
		else {
		if (x >= -375 && soundboolean == true) {
			if (level == 1) {
			eagleSound.play(Audio.PLAY_ENTIRE_SONG);
			}
			soundboolean = false;
		}
		if (x>=860) {
			thirdeagle+=1;
			if (thirdeagle==3) {
				x = -1100;
			}
			else {
			x = -1150;
			}
			soundboolean = true;
		}
		super.update();
	}
	}
}
