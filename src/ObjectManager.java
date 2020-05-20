import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener{
	Player rocket;
	ArrayList<Projectile> PJ = new ArrayList<Projectile>();
	ArrayList<Alien> Alien = new ArrayList<Alien>();
	Random randy = new Random();
	static int score = 0;
	static int getScore() {
		return score;
	}
	ObjectManager(Player rocket) {
		this.rocket = rocket;
	}

	void addProjectile(Projectile Pro) {
		PJ.add(Pro);
	}

	void addAlien() {
		Alien.add(new Alien(randy.nextInt(ApplesofDeath.WIDTH), 0, 50, 50));
	}

	void update() {
		rocket.update();
		for (Alien a : Alien) {
			a.update();
			if (a.y > ApplesofDeath.HEIGHT) {
				a.isActive = false;
			}
		}
		for (Projectile p : PJ) {
			p.update();
			if (p.y < 0) {
				p.isActive = false;
			}
		}
		checkCollision();
		purgeObjects();
	}

	void draw(Graphics g) {
		rocket.draw(g);
		for (Alien a : Alien) {
			a.draw(g);
		}
		for (Projectile p : PJ) {
			p.draw(g);
		}
	}

	void purgeObjects() {
		for (int i = 0; i < Alien.size(); i++) {
			if (Alien.get(i).isActive == false) {
				Alien.remove(i);
			}
		}
		for (int z = 0; z < PJ.size(); z++) {
			if (PJ.get(z).isActive == false) {
				PJ.remove(z);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		addAlien();
	}

	void checkCollision() {
		for (int i = 0; i < Alien.size(); i++) {
			for (int a = 0; a < PJ.size(); a++) {
				if (PJ.get(a).collisionBox.intersects(Alien.get(i).collisionBox)) {
					PJ.get(a).isActive = false;
					Alien.get(i).isActive = false;
					score+=1;
				}
				}
			if (rocket.collisionBox.intersects(Alien.get(i).collisionBox)) {
				Alien.get(i).isActive = false;
				rocket.isActive = false;
			}
		}
	}
}
