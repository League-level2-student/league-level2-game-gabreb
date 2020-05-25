import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class ObjectManager implements ActionListener{
	Player mario;
	ArrayList<Projectile> PJ = new ArrayList<Projectile>();
	ArrayList<Apple> Alien = new ArrayList<Apple>();
	Random randy = new Random();
	static int score = 0;
	Audio Chomp = new Audio("chomp.mp3");
	Timer Timer = new Timer(6000,this);
	public boolean reset = false;
	public boolean freeze = false;
	
	static int getScore() {
		return score;
	}
	ObjectManager(Player rocket) {
		this.mario = rocket;
	}

	void addProjectile(Projectile Pro) {
		PJ.add(Pro);
	}

	void addAlien() {
		Alien.add(new Apple(randy.nextInt(ApplesofDeath.WIDTH-45), 200, 60, 35));
	}

	void update() {
		mario.update();
		for (Apple a : Alien) {
			a.update();
			if (a.y > ApplesofDeath.HEIGHT) {
				a.isActive = false;
				//mario.isActive = false;
				freeze = true;
				Timer.start();
				Alien = new ArrayList<Apple>();
				for (int i = 0; i < 35; i++) {
					Alien.add(new Apple(mario.x,0-(i*29),60,35));
					Alien.get(i).speed = 5;
				}
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
		mario.draw(g);
		for (Apple a : Alien) {
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
		if (freeze == false) {
		addAlien();
		}
		
		if (e.getSource()==Timer) {
			reset = true;
		}
	}

	void checkCollision() {
		for (int i = 0; i < Alien.size(); i++) {
			if (mario.collisionBox.intersects(Alien.get(i).collisionBox)) {
				Alien.get(i).isActive = false;
				if (freeze == false) {
				score+=1;
				}
				Chomp.play(Audio.PLAY_ENTIRE_SONG);
			}
		}
	}
}
