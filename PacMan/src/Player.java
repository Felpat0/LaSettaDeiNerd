import javax.swing.*;

public class Player extends Collider{
	Icon sprites[];
	int direction[] = {0, 0};
	int speed;
	public Player(int x, int y, int w, int h, int speed, Icon[] sprites) {
		super(x, y, w, h);
		this.sprites = sprites;
		this.speed = speed;
	}
	
	public void move(){
		this.x += direction[0] * speed;
		this.y += direction[1] * speed;
	}

}
