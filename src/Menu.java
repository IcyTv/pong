import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("all")
public class Menu extends JPanel implements Runnable{
	private int WIDTH, HEIGHT;
	private Button[] buttons;
	
	public Menu(int w, int h, Main ctrl) {
		WIDTH = w;
		HEIGHT = h;
		new Thread(this).start();
		
		buttons = new Button[3];
		
		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
		this.getActionMap().put("esc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		addMouseListener(new MouseAdapter() {
			
			
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				for(Button b: buttons) {
					if(b == null) {
						return;
					}
					if((x >= b.getX() && x <= b.getX() + b.getW()) && (y >= b.getY() && y <= b.getY() + b.getH())) {
						if(b.getTxt() == "START") {
							ctrl.startGame();
						} else if(b.getTxt() == "EXIT") {
							System.exit(0);
						} else if(b.getTxt() == "SETTINGS") {
							ctrl.goSettings();
						}
					}
				}
			}
		});
	}
	public void run() {
		try{
			while(true){
				Thread.currentThread().sleep(5);
				repaint();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		
		//Font titleFont = new Font("Bit5x3", Font.TRUETYPE_FONT, 100);
		Font baseF = new Font("Courier", Font.TRUETYPE_FONT, 80);
		try {
			baseF = Font.createFont(Font.TRUETYPE_FONT, new File("./bit5x3.ttf"));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font titleFont = baseF.deriveFont(Font.TRUETYPE_FONT, 100);
		g2.setFont(titleFont);
		
		g2.setColor(Color.WHITE);
		int w = g2.getFontMetrics(titleFont).stringWidth("PONG");
		int h = g2.getFontMetrics(titleFont).getHeight();
		g2.drawString("PONG", WIDTH/2 - w/2, h + 30);
		
		//Font font = new Font("Bit5x3", Font.TRUETYPE_FONT, 80);
		Font font = baseF.deriveFont(Font.TRUETYPE_FONT, 80);
		g2.setFont(font);
		
		w = g2.getFontMetrics(font).stringWidth("START");
		h = g2.getFontMetrics(font).getHeight();
		
		if(buttons[0] == null) {
			buttons[0] = new Button(WIDTH/2 - w/2, HEIGHT/4 + 30, w, h, "START");
		}
		
		g2.fillRect(buttons[0].getX(), buttons[0].getY(), buttons[0].getW(), buttons[0].getH());
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[0].getTxt(), buttons[0].getX() + 5, buttons[0].getY() + h - 3);
		
		w = g2.getFontMetrics(font).stringWidth("SETTINGS");
		
		if(buttons[1] == null) {
			buttons[1] = new Button(WIDTH/2 - w/2, 2 * HEIGHT/4, w, h, "SETTINGS");
		}
		
		g2.setColor(Color.WHITE);
		g2.fillRect(buttons[1].getX(), buttons[1].getY(), buttons[1].getW(), buttons[1].getH());
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[1].getTxt(), buttons[1].getX() + 5, buttons[1].getY() + h - 3);
		w = g2.getFontMetrics(font).stringWidth("EXIT");
		
		if(buttons[2] == null) {
			buttons[2] = new Button(WIDTH/2 - w/2, 3 * HEIGHT/4, w, h, "EXIT");
		}
		 
		g2.setColor(Color.WHITE);
		g2.fillRect(buttons[2].getX(), buttons[2].getY(), buttons[2].getW(), buttons[2].getH());
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[2].getTxt(), buttons[2].getX() + 5, buttons[2].getY() + h - 3);
	}
}
