import javax.swing.*;

public class Player extends Collider{
	Icon sprites[];
	int direction[] = {0, 0};
	int lives;
	int speed;
	int score;
	long lastHitTime;
	long invincibilityTime;
	int willTurn; //0 no -1 left -2 right -3 up -4 down
	public Player(int x, int y, int w, int h, int speed, Icon[] sprites) {
		super(x, y, w, h);
		this.sprites = sprites;
		this.speed = speed;
		this.willTurn = 0;
		this.lives = 3;
		this.lastHitTime = 0;
		this.invincibilityTime = 1000;
		this.score = 0;
	}
	
	public void move(Collider[] walls){
		Boolean canMove = true;
		Boolean canTurn = true;
		for(int i = 0; i != walls.length; i++){
			
			//Predict if this will collide
			if(new Collider(x + this.direction[0] * this.speed, y + this.direction[1] * this.speed, width, height, 1).isColliding(walls[i])){
				
				if(this.x == walls[i].x - this.width || this.x == walls[i].x + walls[i].width || this.y == walls[i].y - this.height || this.y == walls[i].y + walls[i].height){
					//No need to change Player coordinates because he is standing(for fixing bug)
				}else{
					if(this.direction[0] == 1)//Right
						this.x = walls[i].x - this.width;
					else if(this.direction[0] == -1)//Left
						this.x = walls[i].x + walls[i].width;
					else if(this.direction[1] == 1) //Down
						this.y = walls[i].y - this.height;
					else if(this.direction[1] == -1) //Up
						this.y = walls[i].y + walls[i].height;
					this.direction[0] = 0;
					this.direction[1] = 0;
					canMove = false;
				}
			}
			
			//If Player will turn, check if it can now
			int nextDir = 0;
			if(willTurn == 1 || willTurn == 3)
				nextDir = -1;
			else if(willTurn == 2 || willTurn == 4)
				nextDir = 1;
			//Actual horizontal
			if(this.direction[0] != 0){
				if(new Collider(this.x + 1, this.y + 2*nextDir, width-2, height-2, 1).isColliding(walls[i]) && willTurn != 0){
					canTurn = false;
				}
			}
			//Actual vertical
			else if(direction[1] != 0){
				if(new Collider(this.x + 2*nextDir, this.y + 1, width-2, height-2, 1).isColliding(walls[i]) && willTurn != 0){
					canTurn = false;
				}
			}
		}
		
		
		if(canTurn && willTurn != 0){
			canTurn = false;
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
			this.x += this.direction[0] * speed;
			this.y += this.direction[1] * speed;
		}
		
		//If it goes outside the screen
		if(this.x >= 750)
			this.x = -52;
		if(this.x < -55)
			this.x = 720;
		
	}
	
	public void checkAndMove(Collider[] walls, int pressed){ //0=none 1=left 2=right 3=up 4=down
		this.willTurn = pressed;
		int turnPoint = 0;
		
		if(this.direction[1] != 0 || this.direction[0] != 0){ //If player is already moving
			//Check if Player can turn after N pixels
			for(int j = 1; j != this.speed+1; j++){
				this.willTurn = pressed;
				int n = 5;
				int c = 5;
				int tempDir0 = 0;
				int tempDir1 = 0;
				
				if(pressed == 1)
					tempDir0 = -1;
				else if(pressed == 2)
					tempDir0 = 1;
				if(pressed == 3)
					tempDir1 = -1;
				else if(pressed == 4)
					tempDir1 = 1;
				
				for(int i = 0; i != walls.length; i++){
					Collider temp;
					if(this.direction[0] != 0){
						n = j;
						c = 5;
						temp = new Collider(
								this.x + (this.width/2) + ((this.width/2)*this.direction[0]) + (n*this.direction[0]), 
								this.y + (this.height/2) + ((this.height/2)*tempDir1)+ (c*tempDir1),
								0,
								0, 1);
					}else{
						n = 5;
						c = j;
						temp = new Collider(
								this.x + (this.width/2) + ((this.width/2)*tempDir0)+ (n*tempDir0), 
								this.y + (this.height/2) + ((this.height/2)*this.direction[1])+ (c*this.direction[1]),
								1,
								1, 1);
					}
					
					if(temp.isColliding(walls[i])){
						this.willTurn = 0;
					}
				}
				//This J is free from walls
				if(this.willTurn != 0){
					turnPoint = j;
				}
			}
		}
		
		//If player has not to turn change his direction
		//If he has to turn, let it walk
		if(this.willTurn == 0 || (this.direction[0] == 0 && this.direction[1] == 0)){
			switch (pressed) {
				case 1:
					this.direction[0] = -1;
					this.direction[1] = 0;
					break;
				case 2:
					this.direction[0] = 1;
					this.direction[1] = 0;
					break;
				case 3:
					this.direction[0] = 0;
					this.direction[1] = -1;
					break;
				case 4:
					this.direction[0] = 0;
					this.direction[1] = 1;
					break;
				default:
					break;
			}
		}
	}
}
