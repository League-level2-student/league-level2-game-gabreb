import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class TridentDraw {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	void draw (Graphics g) {
		if (needImage) {
			loadImage("Anchor.png");
		}
		if (gotImage) {
			g.drawImage(image, 780, 15, 33, 33, null);
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
