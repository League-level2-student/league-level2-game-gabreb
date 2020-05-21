import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class AppleOrchard {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	void draw(Graphics g) {
		if (needImage) {
			loadImage("AppleTree.png");
		}
		if (gotImage) {
			for (int i = 0; i < 2; i++) {
				g.drawImage(image, 100 + (i * 300), 80, 370, 370, null);
			}
			for (int i = 0; i < 3; i++) {
				g.drawImage(image, -80 + (i * 300), 160, 400, 500, null);
			}
			for (int i = 0; i < 2; i++) {
				g.drawImage(image, 100 + (i * 300), 240, 320, 400, null);
			}
			g.drawImage(image, -120, 300, 300, 350, null);
			g.drawImage(image, 650, 300, 300, 350, null);
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
