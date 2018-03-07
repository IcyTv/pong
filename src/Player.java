public class Player {
	private int x;
	private int y;
	private double dy;
	
	private int[] s;
	
	private int[] dim;
	
	public Player(int x_, int y_, int w, int h, int[] size){
		x = x_ - w;
		y = y_ - h;
		s = size;
		dim = new int[]{w, h};
		dy = 0;
	}
//	public void move(String dir){
//		switch(dir){
//			case "UP":
//				y -= 100;
//				break;
//			case "DOWN":
//				y += 100;
//				break;
//			default:
//				System.out.println("Something went Wrong");
//				break;
//		}
//	}
	public void move(){
		y += dy;
		//System.out.println("MOVING");
	}
	public void setDy(double to){
		dy = to;
	}
	public void bind(){
		if(y < 0){
			y = 0;
		} else if(y > s[1] - dim[1]){
			y = s[1] - dim[1];
		}
	}
	public int[] getPos(){
		return new int[]{x, y};
	}
	public int[] getDims(){
		return dim;
	}
	public boolean overlaps(Ball b){
		boolean tmp = (((b.getX() + 10 >= x && b.getX() <= x + dim[0]) || (b.getX() >= x && b.getX() + 10 <= x + dim[0])) && ((b.getY() + 10 >= y && b.getY() <= y + dim[1]) || (b.getY() >= y && b.getY() + 10 <= y + dim[1])));
		double tmp2 = b.getDir();
		while(tmp2 < 0) {
			tmp2 += 360;
		}
		if(tmp){
			if(x > s[0]/2.0){
				tmp = tmp2 >= 270 || tmp2 <= 90;
			} else {
				tmp = tmp2 >= 90 && tmp2 <= 270;
			}
		}
		return tmp;
	}
}
