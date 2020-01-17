import javax.swing.Icon;
import javax.swing.JLabel;

/*Cattini Federico*/

public abstract class Collider {
	int width;
	int height;
	int x;
	int y;
	
	public Collider(int x, int y, int w, int h){
		width = w;
		height = h;
		this.x = x;
		this.y = y;
	}
	
	Boolean isColliding(Collider a){
		if((a.x <= this.x + this.width && a.x >= this.x || a.x + a.width <= this.x + this.width && a.x + a.width >= this.x) || (this.x <= a.x + a.width && this.x >= a.x || this.x + this.width <= a.x + a.width && this.x + this.width >= a.x)){
		
			if((a.y <= this.y + this.height && a.y >= this.y || a.y + a.height <= this.y + this.height && a.y + a.height >= this.y) || (this.y <= a.y + a.height && this.y >= a.y || this.y + this.height <= a.y + a.height && this.y + this.height >= a.y)){
				return true;
			}
		}
		return false;
	}
	
}
