import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private Menu start;
	private Game gameMaster;
	private Settings setting;
	
	public Main(){
		super("Pong");
		setSize(WIDTH, HEIGHT);
		start = new Menu(WIDTH, HEIGHT, this);
		gameMaster = new Game(WIDTH, HEIGHT - 40, this);
		setting = new Settings(WIDTH, HEIGHT, gameMaster, this);
		
		((Component)start).setFocusable(true);
		setBackground(Color.BLACK);
		
		getContentPane().add(start);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startGame() {
		((Component)gameMaster).setFocusable(true);
		
		getContentPane().removeAll();
		getContentPane().invalidate();
		getContentPane().add(gameMaster);
		getContentPane().revalidate();
		gameMaster.grabFocus();
		repaint();
		
	}
	public void goSettings() {
		((Component)setting).setFocusable(true);
		
		getContentPane().removeAll();
		getContentPane().invalidate();
		getContentPane().add(setting);
		getContentPane().revalidate();
		setting.grabFocus();
		repaint();
		
	}
	public void goMenu() {
		
		((Component)start).setFocusable(true);
		
		getContentPane().removeAll();
		getContentPane().invalidate();
		getContentPane().add(start);
		getContentPane().revalidate();
		start.grabFocus();
		repaint();
		
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Main run = new Main();
	}

}