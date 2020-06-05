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
	Timer frameDraw;
	Timer symphony = new Timer(398000, this);
	Timer forlevel2 = new Timer(700,this);
	boolean fl2 = true;
	boolean fl2sm = false;
	boolean eaglelev2 = false;
	Timer forlevel2secondmessage = new Timer(100,this);
	Player mario = new Player(402, 524, 50, 50);
	Egg egg = new Egg(-100, 80, 50, 50, 1000);
	Eagle eagle = new Eagle(-2400, 40, 90, 90);
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
	int eggdrop = new Random().nextInt(ApplesofDeath.WIDTH - 45);
	DrawSplatEgg SplatEgg = new DrawSplatEgg();
	SkyDraw Sky = new SkyDraw();

	GamePanel() {
		titleFont = new Font("Baskerville", Font.ITALIC, 52);
		titleFontEnter = new Font("Baskerville", Font.ITALIC, 20);
		titleFontScore = new Font("Arial", Font.PLAIN, 20);
		frameDraw = new Timer(1000 / 60, this);
		// BeethovensFifthAmazingSymphony.play(Audio.PLAY_ENTIRE_SONG);
		symphony.start();
		frameDraw.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
			mario.isActive = true;
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
		}
		else if (currentState==END) {
			
		}

	}

	void updateMenuState() {
	}

	void updateGameState() {
		// makes the apples and mario move
		manager.update();
		// this moves the eagle
		eagle.update();
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
			currentState = 3;
		}
	}
	void updateLevel2State() {
		mario.left = false;
		mario.right = false;
		
		//code bellow makes apples stopping spawning
		manager.diedtoegg = true;
		manager.Alien = new ArrayList<Apple>();
	
		if (eaglelev2) {
			eagle.speed = 10;
		}
		else {
		eagle.speed = 0;
		}
		eagle.update();
	
		
			
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
		Sky.draw(g);
		orchard.draw(g);
		manager.draw(g);
		eagle.draw(g);
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
		}
		else if (currentState == LEVEL2) {
			updateLevel2State();
		}
		if (e.getSource() == symphony) {
			BeethovensFifthAmazingSymphony.play(Audio.PLAY_ENTIRE_SONG);
		}
		if (e.getSource() == forlevel2&&fl2&&currentState==LEVEL2) {
			JOptionPane.showMessageDialog(null, "Lucky punk, that was the easy part! HAHAHAHAH!");
			forlevel2secondmessage.start();
			fl2 = false;
			fl2sm = true;
		}
		if (e.getSource()==forlevel2secondmessage&&fl2sm&&currentState==LEVEL2) {
			JOptionPane.showMessageDialog(null, "Now shoot the dam bird!");
			fl2sm = false;
			eaglelev2 = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentState == MENU && e.getKeyCode() == KeyEvent.VK_SPACE) {
			JOptionPane.showMessageDialog(null, "No instructions");
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
				fl2 = true;
				eaglelev2 = false;
			} else {
				currentState++;
				if (currentState == GAME) {
					end234 = false;
					startGame();
				} else if (currentState == LEVEL2) {
					eagle = new Eagle(-1800, 40, 60, 60);
					mario = new Player(402, 524, 50, 50);
					manager = new ObjectManager(mario, eagle, egg);
				}
				else if (currentState == END) {
					alienSpawn.stop();
					mario = new Player(402, 524, 50, 50);
					eagle = new Eagle(-2400, 50, 90, 90);
					egg = new Egg(-100, 80, 50, 50, 1000);
					manager = new ObjectManager(mario, eagle, egg);
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
