import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("all")
public class Settings extends JPanel implements Runnable{
	private int WIDTH, HEIGHT;
	private Button[] buttons;
	private Font baseF;
	private boolean reset;
	
	public Settings(int w, int h, Game gm, Main ctrl) {
		new Thread(this).start();
		buttons = new Button[4];
		WIDTH = w;
		HEIGHT = h;
		reset = false;
		
		baseF = new Font("Courier", Font.TRUETYPE_FONT, 90);
		try {
			baseF = Font.createFont(Font.TRUETYPE_FONT, new File("./bit5x3.ttf"));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
		this.getActionMap().put("esc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ctrl.goMenu();
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
						if(b.getTxt() == "BACK") {
							ctrl.goMenu();
						}
						if(b.getTxt() == "RANDOMNESS"){
							int tmp =(Integer.parseInt(b.getSecTxt()) - 5);
							while(tmp < 0){
								tmp += 35;
							}
							gm.setRandomness(tmp);
							b.setSecTxt(Integer.toString(tmp));
						} else if(b.getTxt() == "SPEED"){
							double tmp = Double.parseDouble(b.getSecTxt()) - 0.1;
							while(tmp < 0){
								tmp += 1.0;
							}
							gm.setSpeed(round(tmp, 2));
							b.setSecTxt("" + round(tmp, 2));
						} else if(b.getTxt() == "RESET"){
							reset = !reset;
						}
					}
				}
			}
		});
	}
	
	@Override
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
		
		//Font font = new Font("Bit3x5", Font.TRUETYPE_FONT, 80);
		Font font = baseF.deriveFont(Font.TRUETYPE_FONT, 80);
		g2.setFont(font);
		
		int w = g2.getFontMetrics(font).stringWidth("BACK");
		int h = g2.getFontMetrics(font).getHeight();
		
		if(buttons[0] == null) {
			buttons[0] = new Button(WIDTH/2 - w/2, 3*HEIGHT/4, w, h, "BACK");
		}

		g2.setColor(Color.WHITE);
		g2.fillRect(buttons[0].getX(), buttons[0].getY(), buttons[0].getW(), buttons[0].getH());
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[0].getTxt(), buttons[0].getX() + 5, buttons[0].getY() + h - 3);
		
		w = g2.getFontMetrics(font).stringWidth("RANDOMNESS");
		
		if(buttons[1] == null) {
			buttons[1] = new Button(WIDTH/2 - w/2, 2*HEIGHT/4, w, h, "RANDOMNESS", "20");
		}

		w = g2.getFontMetrics(font).stringWidth(buttons[1].getSecTxt());
		g2.setColor(Color.WHITE);
		g2.fillRect(buttons[1].getX(), buttons[1].getY(), buttons[1].getW(), buttons[1].getH());
		g2.drawString(buttons[1].getSecTxt(), buttons[1].getX() + buttons[1].getW()/2 - w/2, buttons[1].getY() + 2 * h + 5);
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[1].getTxt(), buttons[1].getX() + 5, buttons[1].getY() + h - 3);

		w = g2.getFontMetrics(font).stringWidth("SPEED");
		
		if(buttons[2] == null) {
			buttons[2] = new Button(WIDTH/2 - w/2, HEIGHT/4, w, h, "SPEED", "0.5");
		}

		w = g2.getFontMetrics(font).stringWidth(buttons[2].getSecTxt());
		g2.setColor(Color.WHITE);
		g2.fillRect(buttons[2].getX(), buttons[2].getY(), buttons[2].getW(), buttons[2].getH());
		g2.drawString(buttons[2].getSecTxt(), buttons[2].getX() + buttons[2].getW()/2 - w/2, buttons[2].getY() + 2 * h + 5);
		g2.setColor(Color.BLACK);
		g2.drawString(buttons[2].getTxt(), buttons[2].getX() + 5, buttons[2].getY() + h - 3);
		
		w = g2.getFontMetrics(font).stringWidth("RESET");
		
		if(buttons[3] == null) {
			buttons[3] = new Button(WIDTH/2 - w/2, 40, w, h, "RESET", "");
		}
		//buttons[3].setSecTxt("" + reset);

		//w = g2.getFontMetrics(font).stringWidth(buttons[3].getSecTxt());
		if(reset){
			g2.setColor(Color.BLACK);
		} else {
			g2.setColor(Color.WHITE);
		}
		g2.fillRect(buttons[3].getX(), buttons[3].getY(), buttons[3].getW(), buttons[3].getH());
		g2.drawString(buttons[3].getSecTxt(), buttons[3].getX() + buttons[3].getW()/2 - w/2, buttons[3].getY() + 2 * h + 5);
		if(reset){
			g2.setColor(Color.WHITE);
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.drawString(buttons[3].getTxt(), buttons[3].getX() + 5, buttons[3].getY() + h - 3);
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	public boolean doReset(){
		return reset;
	}
}
