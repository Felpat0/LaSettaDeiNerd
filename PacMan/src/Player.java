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
	
	public void move(Collider[] walls){
		Boolean canMove = true;

		for(int i = 0; i != walls.length; i++){
			//Predict if player will collide
			if(new Collider(x + this.direction[0] * this.speed, y + this.direction[1] * this.speed, width, height, 1).isColliding(walls[i])){
				if(this.direction[0] == 1)
					this.x = walls[i].x - this.width;
				else if(this.direction[0] == -1)
					this.x = walls[i].x + walls[i].width +1;
				else if(this.direction[1] == 1)
					this.y = walls[i].y - this.height;
				else if(this.direction[1] == -1)
					this.y = walls[i].y + walls[i].height +1;
				this.direction[0] = 0;
				this.direction[1] = 0;
				canMove = false;
			}
		}
		if(canMove){
			this.x += direction[0] * speed;
			this.y += direction[1] * speed;
		}
	}

}
