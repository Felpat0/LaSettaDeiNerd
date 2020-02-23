import javax.swing.*;

public class Player extends Collider{
	Icon sprites[];
	int direction[] = {0, 0};
	int speed;
	int willTurn; //0 no -1 left -2 right -3 up -4 down
	public Player(int x, int y, int w, int h, int speed, Icon[] sprites) {
		super(x, y, w, h);
		this.sprites = sprites;
		this.speed = speed;
		this.willTurn = 0;
	}
	
	public void move(Collider[] walls){
		Boolean canMove = true;
		Boolean canTurn = true;
		for(int i = 0; i != walls.length; i++){
			
			//Predict if player will collide
			if(new Collider(x + this.direction[0] * this.speed, y + this.direction[1] * this.speed, width, height, 1).isColliding(walls[i])){
				
				if(this.x == walls[i].x - this.width || this.x == walls[i].x + walls[i].width || this.y == walls[i].y - this.height || this.y == walls[i].y + walls[i].height){
					//No need to change Player coordinates (for fixing bug)
				}else{
					if(this.direction[0] == 1)//Right
						this.x = walls[i].x - this.width;
					else if(this.direction[0] == -1)//Left
						this.x = walls[i].x + walls[i].width;
					else if(this.direction[1] == 1) //Up
						this.y = walls[i].y - this.height;
					else if(this.direction[1] == -1) //Down
						this.y = walls[i].y + walls[i].height;
					this.direction[0] = 0;
					this.direction[1] = 0;
					canMove = false;
				}
			}
			
			//If Player has to turn, check if it can
			if(new Collider(x + 5 + this.direction[0] * this.speed, y + 1 + this.direction[1] * this.speed, width-2, height-2, 1).isColliding(walls[i]) && willTurn != 0){
				canTurn = false;
			}
		}
		
		if(canTurn && willTurn != 0){
			System.out.println("daje");
			switch (this.willTurn) {
			case 1:
				direction[0] = -1;
				direction[1] = 0;
				break;
			case 2:
				direction[0] = 1;
				direction[1] = 0;
				break;
			case 3:
				this.y += direction[1] * speed;
				direction[0] = 0;
				direction[1] = -1;
				break;
			case 4:
				direction[0] = 0;
				direction[1] = 1;
				break;
			default:
				break;
			}
			willTurn = 0;
		}
		if(canMove){
			this.x += direction[0] * speed;
			this.y += direction[1] * speed;
		}		
		
	}

}
