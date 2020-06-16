import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	public static BufferedImage image;
	public static BufferedImage evilgene;
	public static BufferedImage fork;
	public static BufferedImage knife;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	public boolean up = false;
	public boolean down = false;
	public boolean right = false;
	public boolean left = false;

	Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 12;
		if (needImage) {
			loadImage();
		}
	}

	void draw(Graphics g) {
		if (gotImage) {
			if (Eagle.changecharacter) {
				g.drawImage(evilgene, x-10, y, width+25, height+25, null);
			}
			else {
				g.drawImage(image, x, y, width, height, null);
			}
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


	void loadImage() {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream("Mario.png"));
				evilgene = ImageIO.read(this.getClass().getResourceAsStream("evilgene.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}

	public Projectile getProjectile() {
		return new Projectile(x -35 + width / 2, y-60, 75, 75);
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
