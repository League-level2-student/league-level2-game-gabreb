import javax.swing.JFrame;

public class ApplesofDeath {
	JFrame frame;
	GamePanel gamepanel;
	public static final int WIDTH = 840;
	public static final int HEIGHT = 610;
public static void main(String[] args) {
	ApplesofDeath vader = new ApplesofDeath();
	vader.setup();

}
ApplesofDeath() {
	frame = new JFrame();
	gamepanel = new GamePanel();
}
void setup() {
	frame.add(gamepanel);
	frame.setSize(WIDTH,HEIGHT);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	frame.addKeyListener(gamepanel);
}
}





