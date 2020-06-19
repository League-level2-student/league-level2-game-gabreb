import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.image.BufferedImage;
	import java.net.URL;

	import javax.imageio.ImageIO;
	import javax.swing.ImageIcon;
public class BombShow {
		public static BufferedImage image;
		public static boolean needImage = true;
		public static boolean gotImage = false;
		URL url = FlamesofStart.class.getResource("SHKS.gif");
		Image blow = new ImageIcon(url).getImage();
		void draw (Graphics g) {
			if (needImage) {
				loadImage("SHKS.gif");
			}
			if (gotImage) {
				g.drawImage(blow, 0,0, ApplesofDeath.WIDTH,ApplesofDeath.HEIGHT,null);
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
