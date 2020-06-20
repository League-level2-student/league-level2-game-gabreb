import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ObjectManager implements ActionListener {
	Player mario;
	Eagle eagle;
	Egg egg;
	ArrayList<Projectile> PJ = new ArrayList<Projectile>();
	ArrayList<Apple> Alien = new ArrayList<Apple>();
	Random randy = new Random();
	static int score = 0;
	Timer Timer = new Timer(6000, this);
	Timer backtostart = new Timer(1, this);
	Timer SplatTimer = new Timer(1700, this);
	Timer fortheend = new Timer(1800, this);
	Timer tadatimer = new Timer (1,this);
	boolean tadatimerboolena = true;
	boolean birdkillb = false;
	boolean torestart = false;
	boolean tomanyofthese = true;
	boolean tocounterfortheend = false;
	boolean missedtridents = false;
	boolean eggreset = false;
	boolean intersects = true;
	boolean oneatatime = true;
	public boolean reset = false;
	public boolean freeze = false;
	boolean updateforLevel2 = false;
	boolean eagleTRUE = true;
	int numberofApples = 0;
	boolean tostoptheham = false;
	int eggdrop = new Random().nextInt(ApplesofDeath.WIDTH - 45);
	int eggdrop2 = new Random().nextInt(ApplesofDeath.WIDTH - 45);
	int eggdrop3 = new Random().nextInt(ApplesofDeath.WIDTH - 45);
	boolean rand1 = true;
	boolean rand2 = true;
	boolean rand3 = true;
	boolean DidEggSplat = false;
	boolean diedtoegg = false;
	Audio Splat = new Audio("Splat Sound Effect copy.mp3");
	Audio birdkill = new Audio("angry bird.mp3");
	Audio tada = new Audio("Ta-da-orchestra-fanfare.mp3");

	static int getScore() {
		return score;
	}

	ObjectManager(Player rocket, Eagle eagle, Egg egg) {
		this.mario = rocket;
		this.eagle = eagle;
		this.egg = egg;
	}

	void addProjectile(Projectile Pro) {
		PJ.add(Pro);
	}

	void addAlien() {
		if (diedtoegg) {
		} else if (numberofApples < 75) {
			numberofApples += 1;
			Alien.add(new Apple(randy.nextInt(ApplesofDeath.WIDTH - 45), 200, 60, 35));
		}
	}

	void update() {
		if (updateforLevel2) {
			for (Projectile p : PJ) {
				p.update();
				if (p.y < -75) {
					oneatatime = true;
					PJ = new ArrayList<Projectile>();
					if (GamePanel.numtrident == 0) {
						missedtridents = true;
					}
				}
			}
			checkCollision();
			purgeObjects();
		} else {
			mario.update();
			if (eagle.thirdeagle == 1 && rand1) {
				if (eggdrop + 1 == eagle.x || eggdrop - 1 == eagle.x || eggdrop + 2 == eagle.x || eggdrop - 2 == eagle.x
						|| eggdrop == eagle.x) {
					egg = new Egg(eagle.x, 90, 50, 50, mario.x);
					eggdrop = new Random().nextInt(ApplesofDeath.WIDTH - 45);
					rand1 = false;
				}
			} else if (eagle.thirdeagle == 2 && rand2) {
				if (eggdrop2 + 1 == eagle.x || eggdrop2 - 1 == eagle.x || eggdrop2 + 2 == eagle.x
						|| eggdrop2 - 2 == eagle.x || eggdrop2 == eagle.x) {
					egg = new Egg(eagle.x, 90, 50, 50, mario.x);
					eggdrop2 = new Random().nextInt(ApplesofDeath.WIDTH - 45);
					rand2 = false;
				}
			} else if (eagle.thirdeagle == 3 && rand3) {
				if (eggdrop3 + 1 == eagle.x || eggdrop3 - 1 == eagle.x || eggdrop3 + 2 == eagle.x
						|| eggdrop3 - 2 == eagle.x || eggdrop3 == eagle.x) {
					egg = new Egg(eagle.x, 90, 50, 50, mario.x);
					eggdrop3 = new Random().nextInt(ApplesofDeath.WIDTH - 45);
					rand3 = false;
				}
			}
			egg.update();
			for (Apple a : Alien) {
				a.update();
				if (a.y > ApplesofDeath.HEIGHT) {
					a.isActive = false;
					eagle.x = -6000;
					eagle.update();
					egg = new Egg(-100, 80, 50, 50, 1000);
					egg.update();
					freeze = true;
					Timer.start();
					Alien = new ArrayList<Apple>();
					for (int i = 0; i < 35; i++) {
						Alien.add(new Apple(mario.x, 0 - (i * 29), 60, 35));
						Alien.get(i).speed = 5;
					}
				}
			}
		}
			checkCollision();
			purgeObjects();
		}

	void draw(Graphics g) {
		if (mario.collisionBox.intersects(egg.collisionBox) && intersects) {
			Splat.play(Audio.PLAY_ENTIRE_SONG);
			DidEggSplat = true;
			eagle.thirdeagle = 4;
			eagle.update();
			diedtoegg = true;
			Alien = new ArrayList<Apple>();
			intersects = false;
			SplatTimer.start();
		} else {
			for (Apple a : Alien) {
				a.draw(g);
			}
			for (Projectile p : PJ) {
				p.draw(g);
			}
			eagle.draw(g);
			mario.draw(g);
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

		if (e.getSource() == Timer) {
			reset = true;
		}
		if (e.getSource() == SplatTimer && !intersects) {
			JOptionPane.showMessageDialog(null, "You died to a bird's excrement! HAHAHAHAH!");
			intersects = true;
			eggreset = true;
		}
		if (e.getSource() == fortheend && !tocounterfortheend) {
			JOptionPane.showMessageDialog(null,
					"Congratulations! You have won! Press enter \nto restart or admire the scenery! Gaby Rebeiz");
			backtostart.start();
			tocounterfortheend = true;
			torestart = true;
		}
		if (e.getSource() == tadatimer && tadatimerboolena) {
			tadatimerboolena = false;
			tada.play(Audio.PLAY_ENTIRE_SONG);

		}
	}
	

	void checkCollision() {
		for (int i = 0; i < Alien.size(); i++) {
			if (mario.collisionBox.intersects(Alien.get(i).collisionBox)) {
				Alien.get(i).isActive = false;
				if (freeze == false) {
					score += 1;
				}
			}
		}
		if (!tostoptheham) {
			for (int i = 0; i < PJ.size(); i++) {
				if (eagle.collisionBox.intersects(PJ.get(i).collisionBox)) {
					if (PJ.get(i).y < 500) {
						if (!birdkillb) {
							birdkillb = true;
							birdkill.play(Audio.PLAY_ENTIRE_SONG);
						}
						PJ.get(i).speed = -3;
						eagle.downwards = 3;
						
					} else {
						tadatimer.start();
						PJ.get(i).speed = 0;
						eagle.downwards = 0;
						tostoptheham = true;
						fortheend.start();
					}
					eagle.x = PJ.get(i).x;
					eagle.y = PJ.get(i).y - 10;
					eagle.speed = 0;
					eagleTRUE = false;
				}
			}
		}
	}
}
