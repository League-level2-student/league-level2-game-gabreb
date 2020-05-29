import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class DrawSplatEgg {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	void draw (Graphics g) {
		if (needImage) {
			loadImage("EggSplat.png");
		}
		if (gotImage) {
			g.drawImage(image, 0, 0, ApplesofDeath.WIDTH, ApplesofDeath.HEIGHT, null);
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

