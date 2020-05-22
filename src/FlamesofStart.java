import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FlamesofStart {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	URL url = FlamesofStart.class.getResource("Flames.gif");
	Image fire = new ImageIcon(url).getImage();
	void draw (Graphics g) {
		if (needImage) {
			loadImage("Flames.gif");
		}
		if (gotImage) {
			g.drawImage(fire, 0,0, ApplesofDeath.WIDTH,ApplesofDeath.HEIGHT,null);
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
