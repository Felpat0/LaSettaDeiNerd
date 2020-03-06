import javax.swing.Icon;

public class Enemy extends Collider{
	Icon[] sprites;
	public Enemy(int x, int y, int w, int h, int speed, Icon[] sprites) {
		super(x, y, w, h);
		this.sprites = sprites;
	}

}
