import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int LEVEL2 = 2;
	final int END = 3;
	boolean test = true;
	int currentState = MENU;
	Font titleFont;
	Font titleFontEnter;
	Font titleFontScore;
	Font titleFontTrident;
	Timer frameDraw;
	Timer symphony = new Timer(398000, this);
	Timer forlevel2 = new Timer(700, this);
	boolean fl2 = true;
	boolean forbombeffect = false;
	boolean tomoderatemusic = false;
	boolean fl2sm = false;
	boolean bombfalling = false;
	boolean anotherboolean = true;
	static boolean todrawaneagle = false;
	boolean eaglelev2 = false;
	Audio Ohnononono = new Audio("ohno.mp3");
	Timer forlevel2secondmessage = new Timer(100, this);
	Timer bombdeath = new Timer(2500,this);
	boolean tocounterbombdeath = false;
	Player mario = new Player(402, 524, 50, 50);
	Egg egg = new Egg(-100, 80, 50, 50, 1000);
	ArrayList<Egg> bombs = new ArrayList<Egg>();
	Eagle eagle = new Eagle(-2400, 40, 90, 90);
	static boolean changetobomb = false;
	ObjectManager manager = new ObjectManager(mario, eagle, egg);
	public static BufferedImage image;
	public static BufferedImage sky;
	Timer nextStage1 = new Timer(1700, this);
	public static boolean needImage = true;
	public static boolean gotImage = false;
	Timer alienSpawn;
	AppleOrchard orchard = new AppleOrchard();
	FlamesofStart flames = new FlamesofStart();
	static boolean end234 = false;
	static Audio BeethovensFifthAmazingSymphony = new Audio("Beethovens5th.mp3");
	Timer oneforcurrentstate = new Timer(1, this);
	static int numtrident = 5;
	DrawSplatEgg SplatEgg = new DrawSplatEgg();
	SkyDraw Sky = new SkyDraw();
	BombShow bombing = new BombShow();
	TridentDraw trident = new TridentDraw();
	Audio bombfreefall = new Audio("comedy_cartoon_falling_tone.mp3");
	
	GamePanel() {
		titleFont = new Font("Baskerville", Font.ITALIC, 52);
		titleFontEnter = new Font("Baskerville", Font.ITALIC, 20);
		titleFontScore = new Font("Arial", Font.PLAIN, 20);
		titleFontTrident = new Font("Arial", Font.PLAIN, 23);
		frameDraw = new Timer(1000 / 60, this);
		BeethovensFifthAmazingSymphony.play(Audio.PLAY_ENTIRE_SONG);
		symphony.start();
		frameDraw.start();

	}

///my eagle comes early the second and third times
	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
			mario.isActive = true;
			numtrident = 5;
			Eagle.changecharacter = false;
			manager.tocounterfortheend = false;
			anotherboolean = true;
			changetobomb = false;
			bombfalling = false;
			bombs = new ArrayList<Egg>();
			forbombeffect = false;
			tomoderatemusic = false;
			tocounterbombdeath = false;
			manager.tadatimerboolena = true;
			mario = new Player(402, 524, 50, 50);
			egg = new Egg(-100, 80, 50, 50, 1000);
			eagle = new Eagle(-2400, 50, 90, 90);
			manager = new ObjectManager(mario, eagle, egg);
		} else if (currentState == GAME) {
			if (end234) {
				drawGameState(g);
				end234 = false;
				JOptionPane.showMessageDialog(null, "You died to an Apple Barrage! Press enter to restart!");
				oneforcurrentstate.start();
			} else {
				if (manager.DidEggSplat == false) {
					drawGameState(g);
				} else {
					Sky.draw(g);
					orchard.draw(g);
					SplatEgg.draw(g);
				}
			}
		} else if (currentState == LEVEL2) {
			forlevel2.start();
			drawLevel2State(g);
		} else if (currentState == END) {

		}

	}

	void updateMenuState() {
	}

	void updateGameState() {
		// makes the apples and mario move
		manager.update();
		// this moves the eagle
		eagle.update(GAME);
		if (manager.reset) {
			end234 = true;
			mario = new Player(402, 524, 50, 50);
			egg = new Egg(-100, 80, 50, 50, 1000);
			eagle = new Eagle(-2400, 50, 90, 90);
			manager = new ObjectManager(mario, eagle, egg);
		}
		if (manager.eggreset) {
			currentState = MENU;
			mario = new Player(402, 524, 50, 50);
			egg = new Egg(-100, 80, 50, 50, 1000);
			eagle = new Eagle(-2400, 50, 90, 90);
			manager = new ObjectManager(mario, eagle, egg);
		}
		if (manager.freeze) {
			mario.left = false;
			mario.right = false;
		}
		if (manager.score == 75) {
			eagle = new Eagle(-1400, 40, 80, 80);
			mario = new Player(402, 524, 50, 50);
			manager = new ObjectManager(mario, eagle, egg);
			currentState = 2;
			manager.updateforLevel2 = true;
		}
	}

	void updateLevel2State() {
		if (anotherboolean) {
			mario.left = false;
			mario.right = false;
		}
		manager.diedtoegg = true;
		manager.Alien = new ArrayList<Apple>();
		if (manager.torestart) {
			currentState = MENU;
			mario = new Player(402, 524, 50, 50);
			eagle = new Eagle(-2400, 50, 90, 90);
			egg = new Egg(-100, 80, 50, 50, 1000);
			manager = new ObjectManager(mario, eagle, egg);
			manager.updateforLevel2 = false;
			bombs = new ArrayList<Egg>();
		}
		if (manager.missedtridents && anotherboolean) {
			eagle.x = -400;
			eagle.speed = 0;
			anotherboolean = false;
			Ohnononono.play(Audio.PLAY_ENTIRE_SONG);
			mario.left = true;
			mario.right = true;
			JOptionPane.showMessageDialog(null, "Oh no no no no no no no no");
			JOptionPane.showMessageDialog(null, "You can run, but you can't hide!");
			eagle.speed = 4;
			todrawaneagle = true;
		}
		if (eaglelev2 && manager.eagleTRUE && anotherboolean) {
			eagle.speed = 8;
		} else if (manager.eagleTRUE && anotherboolean) {
			eagle.speed = 0;
		}
		eagle.update(LEVEL2);
		manager.update();
		mario.update();
	}

	void drawMenuState(Graphics g) {
		flames.draw(g);
		g.setFont(titleFont);
		g.setColor(Color.RED);
		g.drawString("Apples of Death", 275, 125);
		g.setFont(titleFontEnter);
		g.drawString("Press ENTER to start", 350, 350);
		g.setFont(titleFontEnter);
		g.drawString("Press SPACE for instructions", 320, 550);
	}

	void drawGameState(Graphics g) {
		Sky.draw(g);
		orchard.draw(g);
		manager.draw(g);
		eagle.draw(g);
		g.setColor(Color.RED);
		g.setFont(titleFontScore);
		g.drawString("score: " + manager.getScore(), 27, 30);
	}

	void drawLevel2State(Graphics g) {
		if (forbombeffect) {
			bombfreefall.stop();
			if (!tomoderatemusic) {
				Audio bombblow = new Audio("bigboom.wav");
				tomoderatemusic = true;
				}
			bombing.draw(g);
			bombdeath.start();
		} else {
			Sky.draw(g);
			orchard.draw(g);
			trident.draw(g);
			// eagle.draw(g);
			g.setFont(titleFontTrident);
			g.setColor(Color.RED);
			g.drawString("" + numtrident, 817, 40);
			manager.draw(g);
			if (todrawaneagle) {
				changetobomb = true;
				eagle.draw(g);
				eagle.speed = 8;
				if (eagle.x >= -40 && eagle.x <= 840 && todrawaneagle) {
					if (!bombfalling) {
						bombfalling = true;
						bombfreefall.play(Audio.PLAY_ENTIRE_SONG);
					}
					if (eagle.x % 10 == 0) {
						Egg bomb = new Egg(eagle.x, 50, 70, 45, 1000);
						bomb.speed = 2;
						bombs.add(bomb);
					}
					if (eagle.x >= 840) {
						eagle.x = -80;
						eagle.width = 110;
						eagle.height = 110;
						eagle.speed = 4;
						eagle.draw(g);
						eagle.update();
						todrawaneagle = false;
					}
				}
			}
			for (int i = 0; i < bombs.size(); i++) {
				if (mario.collisionBox.intersects(bombs.get(i).collisionBox)) {
					forbombeffect = true;
				} else {
					bombs.get(i).draw(g);
					bombs.get(i).update();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == oneforcurrentstate) {
			oneforcurrentstate.stop();
			currentState = MENU;
		}
		repaint();
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == LEVEL2) {
			updateLevel2State();
		}
		if (e.getSource() == symphony) {
			BeethovensFifthAmazingSymphony.play(Audio.PLAY_ENTIRE_SONG);
		}
		if (e.getSource() == forlevel2 && fl2 && currentState == LEVEL2) {
			JOptionPane.showMessageDialog(null, "Lucky punk, that was the easy part! HAHAHAHAH!");
			forlevel2secondmessage.start();
			fl2 = false;
			fl2sm = true;
		}
		if (e.getSource() == forlevel2secondmessage && fl2sm && currentState == LEVEL2) {
			JOptionPane.showMessageDialog(null, "Now use the space bar to shoot the damn bird!");
			fl2sm = false;
			eaglelev2 = true;
		}
		if (e.getSource() == bombdeath && !tocounterbombdeath) {
			currentState = MENU;
			bombdeath.stop();
			tocounterbombdeath = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentState == MENU && e.getKeyCode() == KeyEvent.VK_SPACE) {
			JOptionPane.showMessageDialog(null, "Use the arrow keys to move and catch the apples\n    to survive. PS. The eagle is not your friend!");
		}
		if (currentState == LEVEL2 && e.getKeyCode() == KeyEvent.VK_SPACE && manager.oneatatime && numtrident > 0) {
			manager.addProjectile(mario.getProjectile());
			numtrident -= 1;
			manager.oneatatime = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
				fl2 = true;
				eaglelev2 = false;
				numtrident = 5;
				anotherboolean = true;
				Eagle.changecharacter = false;
				manager.tocounterfortheend = false;
				changetobomb = false;
				bombs = new ArrayList<Egg>();
				bombfalling = false;
				forbombeffect = false;
				tomoderatemusic = false;
				tocounterbombdeath = false;
				manager.tadatimerboolena = true;
			} else {
				currentState++;
				if (currentState == GAME) {
					end234 = false;
					startGame();
				} else if (currentState == LEVEL2) {
					eagle = new Eagle(-1400, 40, 80, 80);
					mario = new Player(402, 524, 50, 50);
					manager = new ObjectManager(mario, eagle, egg);
					manager.updateforLevel2 = true;
				} else if (currentState == END) {
					alienSpawn.stop();
					mario = new Player(402, 524, 50, 50);
					eagle = new Eagle(-2400, 50, 90, 90);
					egg = new Egg(-100, 80, 50, 50, 1000);
					manager = new ObjectManager(mario, eagle, egg);
					manager.updateforLevel2 = false;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && manager.freeze == false) {

			mario.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && manager.freeze == false) {

			mario.right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			mario.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			mario.right = false;
		}
	}

	void startGame() {
		alienSpawn = new Timer(600, manager);
		alienSpawn.start();
		manager.score = 0;
	}

}
