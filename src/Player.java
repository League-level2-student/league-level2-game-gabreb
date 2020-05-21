import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	public boolean up = false;
	public boolean down = false;
	public boolean right = false;
	public boolean left = false;

	Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 8;
		if (needImage) {
			loadImage("Mario.png");
		}
	}

	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
	}

	public void right() {
		if (x < 790) {
			x+=speed;
		}
	}

	public void left() {
		if (x > 5) {
			x-=speed;
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

	public Projectile getProjectile() {
		return new Projectile(x + width / 2, y, 10, 10);
	}

	void update() {
	
		if (right==true) {
			right();
		}
		if (left==true) {
			left();
		}
		super.update();
	}
}
