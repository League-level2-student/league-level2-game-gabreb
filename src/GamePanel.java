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
	GamePanel() {
		titleFont = new Font("Arial", Font.PLAIN, 48);
		titleFontEnter = new Font("Arial", Font.PLAIN, 20);
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		GamePanel Panel = new GamePanel();
		if (currentState == MENU) {
			drawMenuState(g);
			mario.isActive = true;
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}

	}

	void updateMenuState() {
	}

	void updateGameState() {
		manager.update();
		if (mario.isActive == false) {
			currentState = END;
			mario = new Player(402, 524, 50, 50);
			manager = new ObjectManager(mario); 
		}
	}

	void updateEndState() {
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
		g.setFont(titleFontEnter);
		g.drawString("score: " + manager.getScore(), 27, 30);
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, ApplesofDeath.WIDTH, ApplesofDeath.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Game Over", 120, 125);
		g.setFont(titleFontEnter);
		g.drawString("You killed " + manager.getScore() + " enemies", 160, 350);
		g.setFont(titleFontEnter);
		g.drawString("Press Enter to Restart", 150, 550);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("action");
		repaint();
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
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
					"Use the arrow keys to move. The space bar to fire. And try not to die!");
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
			} else {
				currentState++;
				if (currentState == GAME) {
					startGame();
				}
				else if (currentState == END) {
					alienSpawn.stop();
					mario = new Player(402, 524, 50, 50);
					manager = new ObjectManager(mario); 
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		
				mario.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
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
