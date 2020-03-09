import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Icon;

public class Enemy extends Collider{
	Icon[] sprites;
	int speed;
	int direction[] = {0, 0};
	int oldDirection[] = {0, 0};
	int turningDirection[] = {1, 1, 1, 1};
	Boolean canTurn;
	
	public Enemy(int x, int y, int w, int h, int speed, Icon[] sprites) {
		super(x, y, w, h);
		this.sprites = sprites;
		this.speed = speed;
		this.canTurn = false;
		while(direction[0] == direction[1])
			this.direction[ThreadLocalRandom.current().nextInt(0, 2)] = ThreadLocalRandom.current().nextInt(0, 3) - 1;
	}
	
	public void move(Collider[] oldWalls){
		Collider[] walls = new Collider[oldWalls.length + 2];
		for(int i = 0; i != oldWalls.length; i++)
			walls[i] = oldWalls[i];
		walls[oldWalls.length] = new Collider(0, 6, 1, 1);
		walls[oldWalls.length + 1] = new Collider(12, 6, 1, 1);
		Boolean canMove = true;
		canTurn = true;
		turningDirection[0] = 1; //Left
		turningDirection[1] = 1; //Right
		turningDirection[2] = 1; //Up
		turningDirection[3] = 1; //Down
		
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
						this.oldDirection[0] = this.direction[0];
						this.oldDirection[1] = this.direction[1];
						canMove = false;
					}
				}
				
				if(this.direction[0] != 0){
					if(this.direction[0] == -1)
						this.turningDirection[1] = 0;
					else
						this.turningDirection[0] = 0;
					
					if(new Collider(this.x + 1, this.y + 2, width-2, height-2, 1).isColliding(walls[i])){
						turningDirection[3] = 0;
					}
					
					if(new Collider(this.x + 1, this.y - 2, width-2, height-2, 1).isColliding(walls[i])){
						turningDirection[2] = 0;
					}
					
					
				}else if(direction[1] != 0){
					if(this.direction[1] == -1)
						this.turningDirection[3] = 0;
					else
						this.turningDirection[2] = 0;
					
					if(new Collider(this.x + 2, this.y + 1, width-2, height-2, 1).isColliding(walls[i])){
						turningDirection[1] = 0;
					}
					if(new Collider(this.x - 2, this.y + 1, width-2, height-2, 1).isColliding(walls[i])){
						turningDirection[0] = 0;
					}
				
				}
				
		}
		
		if((this.direction[1] != 0 && (turningDirection[0] != 0 || turningDirection[1] != 0)) || (this.direction[0] != 0 && (turningDirection[2] != 0 || turningDirection[3] != 0))|| (this.direction[0] == this.direction[1] && canMove)){	
				Boolean ok = false;
				while(!ok){
					int rand = ThreadLocalRandom.current().nextInt(0, 4);
					//System.out.println(rand + "    " + oldDirection[0] + "    " + turningDirection[0]);
					switch (rand) {
					case 0:
						if(this.turningDirection[0] == 1 && oldDirection[0] != 1){
							ok = true;
							this.direction[0] = -1;
							this.direction[1] = 0;
						}
						break;
					case 1:
						if(this.turningDirection[1] == 1 && oldDirection[0] != -1){
							ok = true;
							this.direction[0] = 1;
							this.direction[1] = 0;
						}
						break;
					case 2:
						if(this.turningDirection[2] == 1 && oldDirection[1] != 1){
							ok = true;
							this.direction[1] = -1;
							this.direction[0] = 0;
						}
						break;
					case 3:
						if(this.turningDirection[3] == 1 && oldDirection[1] != -1){
							ok = true;
							this.direction[1] = 1;
							this.direction[0] = 0;
						}
						break;
					default:
						break;
					}
				}
		}

		if(canMove){
			this.x += this.direction[0] * speed;
			this.y += this.direction[1] * speed;
		}
	}
}
