import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("all")
public class Game extends JPanel implements Runnable{
	
	private Player[] players;
	private Ball b;
	
	private int GWIDTH;
	private int GHEIGHT;
	
	private int[] score;
	private static final int MAXSCORE = 10;
	
	private double speed;
	
	private Audio[] sounds;
	
	private Timer tm;
	private Font baseF;
	
	public boolean won;
	
	public Game(int w, int h, Main ctrl) {
		won = false;
		GWIDTH = w;
		GHEIGHT = h;
		speed = 0.5;
		//System.out.println(GWIDTH + " " + GHEIGHT);
		//new Thread(this).start();
		players = new Player[2];
		score = new int[] {0, 0};
		if(Math.random() > 0.5){
			b = new Ball(3, w, h, 20);
		} else {
			b = new Ball(-3, w, h, 20);
		}
		players[0] = new Player(50, (int)h/2, 10, 100, new int[]{w, h});
		players[1] = new Player(w-50, (int)h/2, 10, 100, new int[]{w, h});
		

		baseF = new Font("Courier", Font.TRUETYPE_FONT, 90);
		try {
			baseF = Font.createFont(Font.TRUETYPE_FONT, new File("./bit5x3.ttf"));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sounds = new Audio[] {
				new Audio("./BallPaddle.wav"),
				new Audio("./BallWall.wav"),
				new Audio("./Score.wav")
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.addKeyListener(new KeyPressed(players));
		
		
		this.getInputMap().put(KeyStroke.getKeyStroke("R"), "r");
		this.getActionMap().put("r", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
		this.getActionMap().put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(b.isStopped()) {
					b.start();
				}
			}
		});
		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
		this.getActionMap().put("esc", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ctrl.goMenu();
			}
		});
		tm = new Timer(5, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Running");
				repaint();
				
			}
			
		});
		tm.start();
	}
	
	@Override
	public void run(){
//		try{
//			while(true){
//				Thread.currentThread().sleep(5);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		
		g2.setColor(Color.WHITE);
		
		//Font font = new Font("Bit5x3", Font.TRUETYPE_FONT, 80);
		Font font = baseF.deriveFont(Font.TRUETYPE_FONT, 80);
		g2.setFont(font);
		if(won) {
			if(score[0] >= MAXSCORE) {
				int w = g2.getFontMetrics(font).stringWidth("PLAYER 1 WON");
				g2.drawString("PLAYER 1 WON", GWIDTH/2 - w/2, GHEIGHT/2);
			} else {
				int w = g2.getFontMetrics(font).stringWidth("PLAYER 2 WON");
				g2.drawString("PLAYER 2 WON", GWIDTH/2 - w/2, GHEIGHT/2);
			}
			font = baseF.deriveFont(Font.TRUETYPE_FONT, 30);
			int w = g2.getFontMetrics(font).stringWidth("RESET BY PRESSING \"R\"");
			g2.setFont(font);
			g2.drawString("RESET BY PRESSING \"R\"", GWIDTH/2 - w/2, GHEIGHT/2 + 50);
			w = g2.getFontMetrics(font).stringWidth("GO BACK BY PRESSING \"ESC\"");
			g2.drawString("GO BACK BY PRESSING \"ESC\"", GWIDTH/2 - w/2, GHEIGHT/2 + 100);
			
			return;
		}
		
		g2.setColor(new Color(255, 255, 255, 70));

		for(int i = 0; i < GHEIGHT; i += 50){
			g2.fillRect(GWIDTH/2 - 5, i, 10, 30);
		}
		
		g2.setColor(Color.WHITE);
		
		int w1 = g2.getFontMetrics(font).stringWidth("" + score[0]);
		int w2 = g2.getFontMetrics(font).stringWidth("" + score[1]);
		g2.drawString("" + score[0], GWIDTH/4 - w1/2, 100);
		g2.drawString("" + score[1], 3 * GWIDTH/4 - w1/2, 100);
		
		for(Player p: players){
			
			p.bind();
			int[] pos = p.getPos();
			int[] dim = p.getDims();
			g2.fillRect(pos[0], pos[1], dim[0], dim[1]);
			if(p.overlaps(b)){
				chSpeed(speed);
				sounds[0].play();
				//System.out.println("Overlaps");
				int[][] a = new int[2][4];
				for(int i = 0; i < 2; i++) {
					int b = 0;
					for(int n: players[i].getPos()) {
						a[i][b] = n;
						b++;
					}
					a[i][2] = p.getDims()[1];
					a[i][3] = 0;
				}
				b.chDir(a, false);
				break;
			}
			p.move();
		}
		if(b.getY() <= 0 || b.getY() + 10 >= GHEIGHT) {
			b.chDir(new int[][] {{0, 0, 0, GWIDTH}, {0, GHEIGHT, 0, GWIDTH}}, true);
			sounds[1].play();
		}
		b.move();
		if(b.bind() != 0) {
			b.setMag(3);
			sounds[2].play();
			if(b.bind() == 1) {
				score[0] += 1;
			} else {
				score[1] += 1;
			}
			for(int i : score) {
				if(i >= MAXSCORE) {
					g2.clearRect(0, 0, getSize().width, getSize().height);
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, getSize().width, getSize().height);
					if(score[0] > MAXSCORE) {
						int w = g2.getFontMetrics(font).stringWidth("PLAYER 1 WON");
						g2.drawString("PLAYER 1 WON", GWIDTH - w/2, GHEIGHT/2);
					} else {
						int w = g2.getFontMetrics(font).stringWidth("PLAYER 2 WON");
						g2.drawString("PLAYER 2 WON", GWIDTH - w/2, GHEIGHT/2);
					}
					won = true;
				}
			}
		}
		b.reset(b.bind(), false);
		g2.fillRect(b.getX(), b.getY(), b.getW(), b.getH());
	}
	public void setRandomness(int r) {
		b.setRandomness(r);
	}
	public void reset(boolean comp){
		int i = 1;
		if(b.getDir() > 180){
			i = -1;
		}
		b.reset(i, comp);
	}
	public void chSpeed(double d){
		b.chMag(d);
	}
	public void setSpeed(double s){
		speed = s;
	}
	public void reset() {
		if(Math.random() > 0.5){
			b.reset(1);
		} else {
			b.reset(-1);
		}
		won = false;
		score[0] = 0;
		score[1] = 0;
	}
}

