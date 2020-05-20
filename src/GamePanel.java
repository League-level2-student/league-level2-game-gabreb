import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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
	Player rocketship = new Player(225, 698, 40, 40);
	ObjectManager manager = new ObjectManager(rocketship);
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	Timer alienSpawn;

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
			rocketship.isActive = true;
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
		if (rocketship.isActive == false) {
			currentState = END;
			rocketship = new Player(225,668,50,50);
			manager = new ObjectManager(rocketship); 
		}
	}

	void updateEndState() {
	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, ApplesofDeath.WIDTH, ApplesofDeath.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Apples of Death", 25, 125);
		g.setFont(titleFontEnter);
		g.drawString("Press ENTER to start", 150, 350);
		g.setFont(titleFontEnter);
		g.drawString("Press SPACE for instructions", 120, 550);
	}

	void drawGameState(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,ApplesofDeath.WIDTH,ApplesofDeath.HEIGHT);
		if (needImage) {
			loadImage("AppleTree.png");
		}
		if (gotImage) {
			for (int i = 0; i < 2; i++) {
				g.drawImage(image,100+(i*300),80,370,370,null);
			}
			for (int i = 0; i < 3; i++) {
				g.drawImage(image, -80+(i*300), 160, 400, 500, null);
			}
			for (int i = 0; i < 2; i++) {
				g.drawImage(image,100+(i*300),240,320,400,null);
			}
			g.drawImage(image, -120,300,300,350,null);
			g.drawImage(image, 650,300,300,350,null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, ApplesofDeath.WIDTH, ApplesofDeath.HEIGHT);
		}
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
		if (currentState == GAME && e.getKeyCode() == KeyEvent.VK_SPACE) {
			manager.addProjectile(rocketship.getProjectile());
		}
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
					rocketship = new Player(225,668,50,50);
					manager = new ObjectManager(rocketship); 
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			
				rocketship.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				rocketship.down = true;
			
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		
				rocketship.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
				rocketship.right = true;
			}
		}
	

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			
			rocketship.up = false;
	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			rocketship.down = false;
		
	} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	
			rocketship.left = false;
	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		
			rocketship.right = false;
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
		alienSpawn = new Timer(200, manager);
		alienSpawn.start();
		manager.score = 0;
	}

}
