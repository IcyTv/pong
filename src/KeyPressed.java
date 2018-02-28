import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyPressed implements KeyListener{
	private Player[] ctrl;
	private int[] cheatSeq;
	private List<Integer> inpSeq;
	private boolean active;
	
	private double YCHANGE;
	
	public KeyPressed(Player p1, Player p2){
		ctrl = new Player[]{p1, p2};
		cheatSeq = new int[] {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};
		inpSeq = new ArrayList<Integer>();
		YCHANGE = 5;
		active = true;
	}
	public KeyPressed(Player[] ps){
		this(ps[0], ps[1]);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int idx = inpSeq.size();
		inpSeq.add(e.getKeyCode());
		if(active && inpSeq.get(idx) == cheatSeq[idx]) {
			if(inpSeq.size() == cheatSeq.length) {
				System.out.println("Cheat activated");
				YCHANGE = 20;
				active = false;
			}
		} else if(active){
			inpSeq = new ArrayList<Integer>();
			
		}
		
		if(e.getKeyCode() == 38){
			setDy(1, -1);
		} else if(e.getKeyCode() == 40){
			setDy(1, 1);
		} else if(e.getKeyCode() == 87){
			setDy(0, -1);
		} else if(e.getKeyCode() == 83){
			setDy(0, 1);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 38){
			setDy(1, 0);
		} else if(e.getKeyCode() == 40){
			setDy(1, 0);
		} else if(e.getKeyCode() == 87){
			setDy(0, 0);
		} else if(e.getKeyCode() == 83){
			setDy(0, 0);
		}
	}
	private void setDy(int i, int c){
		ctrl[i].setDy(c * YCHANGE);
	}
}
