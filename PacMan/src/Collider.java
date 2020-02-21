import javax.swing.Icon;
import javax.swing.JLabel;

/*Cattini Federico*/

public class Collider {
	int width;
	int height;
	int x;
	int y;
	
	public Collider(int x, int y, int w, int h){
		width = (w * 56) -1;
		height = (h * 56) -1;
		this.x = x * 56;
		this.y = y * 56;
	}
	
	public Collider(int x, int y, int w, int h, int t){
		width = w;
		height = h;
		this.x = x;
		this.y = y;
	}
	
	
	Boolean isColliding(Collider a){
		//First check if x coordinates match
		if((a.x < this.x + this.width && a.x > this.x || a.x + a.width < this.x + this.width && a.x + a.width > this.x) || (this.x < a.x + a.width && this.x > a.x || this.x + this.width < a.x + a.width && this.x + this.width > a.x)){
			//Then if y coordinates match
			if((a.y < this.y + this.height && a.y > this.y || a.y + a.height < this.y + this.height && a.y + a.height > this.y) || (this.y < a.y + a.height && this.y > a.y || this.y + this.height < a.y + a.height && this.y + this.height > a.y)){
				return true;
			}
		}
		return false;
	}
	
}
