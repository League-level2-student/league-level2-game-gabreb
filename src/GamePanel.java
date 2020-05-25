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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont;
	Font titleFontEnter;
	Font titleFontScore;
	Timer frameDraw;
	Player mario = new Player(402, 524, 50, 50);
	ObjectManager manager = new ObjectManager(mario);
	public static BufferedImage image;
	public static BufferedImage sky;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	Timer alienSpawn;
	AppleOrchard orchard = new AppleOrchard();
	FlamesofStart flames = new FlamesofStart();
	static boolean end234 = true;
	Audio BeethovensFifthAmazingSymphony = new Audio("Beethovens5th.mp3");
	Timer oneforcurrentstate = new Timer(1000, this);
	GamePanel() {
		titleFont = new Font("Baskerville", Font.ITALIC, 52);
		titleFontEnter = new Font("Baskerville", Font.ITALIC, 20);
		titleFontScore = new Font("Arial", Font.PLAIN,20);
		frameDraw = new Timer(1000 / 60, this);
		BeethovensFifthAmazingSymphony.play(Audio.PLAY_ENTIRE_SONG);
		frameDraw.start();
	
	}

	@Override
	public void paintComponent(Graphics g) {
		
		if (currentState == MENU) {
			drawMenuState(g);
			mario.isActive = true;
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawGameState(g);
			if (end234) {
				end234 = false;
				JOptionPane.showMessageDialog(null, "You died to an Apple Barrage! Press enter to restart!");
				oneforcurrentstate.start();
			}
		}

	}

	void updateMenuState() {
	}

	void updateGameState() {
		manager.update();
		if (manager.reset == true) {
			currentState = END;
			mario = new Player(402, 524, 50, 50);
			manager = new ObjectManager(mario); 
		}
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
		if (needImage) {
			loadImage("Sky.jpg");
		}
		if (gotImage) {
			g.drawImage(image, 0,0, ApplesofDeath.WIDTH,ApplesofDeath.HEIGHT,null);
		}
		orchard.draw(g);
		manager.draw(g);
		g.setColor(Color.RED);
		g.setFont(titleFontScore);
		g.drawString("score: " + manager.getScore(), 27, 30);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("action");
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentState == MENU && e.getKeyCode() == KeyEvent.VK_SPACE) {
			JOptionPane.showMessageDialog(null,
					"No instructions");
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
			} else {
				currentState++;
				if (currentState == GAME) {
					end234 = true;
					startGame();
				}
				else if (currentState == END) {
					alienSpawn.stop();
					mario = new Player(402, 524, 50, 50);
					manager = new ObjectManager(mario); 
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

	void startGame() {
		alienSpawn = new Timer(600, manager);
		alienSpawn.start();
		manager.score = 0;
	}

}
