import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

/*
 * TO-DO
 * - Fix player (when pressing button too close to a wall)
 * - Fix collisions with enemies
 * - Add animations
 * - Add death screen
 * - Add sounds
 * - Make the code look BEEEEEEEEEEEAUTIFUL
 * - Random map generation?
 */


public class MainWindow extends JFrame{
	public static int cellSize = 56;
	Panel panel;
	JLabel playerImage;
	JLabel[] enemyImages;
	JLabel[] livesImage;
	Player player;
	Enemy[] enemy;
	ArrayList<Wall> points = new ArrayList<>();
	int map[][];
	Collider walls[] = {
			//External Walls
			new Collider(0, 0, 13, 1) {}, //hor up
			new Collider(0, 12, 13, 1) {}, //hor down
			new Collider(0, 1, 1, 5) {}, //vertical left 1
			new Collider(0, 7, 1, 5) {}, //vertical left 2
			new Collider(12, 1, 1, 5) {}, //vertical right 1
			new Collider(12, 7, 1, 5) {}, //vertical right 2
			new Collider(2, 2, 3, 1) {}, //vertical right 2
			new Collider(2, 3, 1, 1) {}, //vertical right 2
			new Collider(6, 2, 1, 5) {}, //vertical right 2
			new Collider(8, 2, 3, 1) {}, //vertical right 2
			new Collider(10, 3, 1, 1) {}, //vertical right 2
			new Collider(5, 9, 3, 2) {}, //vertical right 2
			new Collider(1, 7, 3, 1) {}, //vertical right 2
			new Collider(2, 3, 1, 1) {}, //vertical right 2
			new Collider(2, 5, 3, 1) {}, //vertical right 2
			new Collider(4, 4, 1, 1) {}, //vertical right 2
			new Collider(8, 4, 1, 1) {}, //vertical right 2
			new Collider(8, 5, 3, 1) {}, //vertical right 2
			new Collider(9, 7, 3, 1) {}, //vertical right 2
			new Collider(2, 9, 2, 2) {}, //vertical right 2
			new Collider(9, 9, 2, 2) {}, //vertical right 2
			new Collider(5, 7, 3, 1) {}, //vertical right 2
	};
	Collider fruits[];
	Icon playerSprites[] = {new ImageIcon(getClass().getResource("player.png"))};
	Icon enemySprites[] = {new ImageIcon(getClass().getResource("ghost.png"))};
	
	public MainWindow(){
		super("Pac-Man");
		//Game size: 728x728
		setSize(728+6, 728+29+70);
		panel = new Panel();
		map = new int[13][13];
		fruits = new Collider[79];
		
		addWalls();
		addFruits();
		
		player = new Player(1, 3, 1, 1, 4, playerSprites);
		enemy = new Enemy[3];
		enemy[0] = new Enemy(5, 1, 1, 1, 4, enemySprites);
		enemy[1] = new Enemy(6, 1, 1, 1, 4, enemySprites);
		enemy[2] = new Enemy(7, 1, 1, 1, 4, enemySprites);

		playerImage = new JLabel(player.sprites[0]);
		enemyImages = new JLabel[3];
		livesImage = new JLabel[3];
		for(int i = 0; i != 3; i++){
			enemyImages[i] = new JLabel(enemy[0].sprites[0]);
			enemyImages[i] = new JLabel(enemy[1].sprites[0]);
			enemyImages[i] = new JLabel(enemy[2].sprites[0]);
			livesImage[i] = new JLabel(player.sprites[0]);
		}
		
		
		add(playerImage);
		
		//Add enemy's and live's images
		for(int i = 0; i != enemy.length; i++){
			add(enemyImages[i]);
			enemyImages[i].setBounds(enemy[i].x, enemy[i].y, enemy[i].height, enemy[i].width);
			add(livesImage[i]);
			livesImage[i].setBounds(10 + i*cellSize, 737, player.height, player.width);
		}
		
		playerImage.setBounds(player.x, player.y, player.height, player.width);
		gameCycle();
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		addKeyListener(new Listener1());
        setLocationRelativeTo(null);
        setResizable(false);
		setVisible(true);
	}
	
	public void gameCycle(){
		Timer t = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.move(walls);
				playerImage.setBounds(player.x, player.y, player.height, player.width);
				
				for(int i = 0; i != enemy.length; i++){
					enemy[i].move(walls);
					enemyImages[i].setBounds(enemy[i].x, enemy[i].y, enemy[i].height, enemy[i].width);
					if(player.isColliding(enemy[i]) && playerImage.isVisible()){
						
						if(System.currentTimeMillis() - player.lastHitTime > player.invincibilityTime){
							player.lastHitTime = System.currentTimeMillis();
							player.lives--;
							
							if(player.lives == 0)
								playerImage.setVisible(false);
						}
					}
				}
				
				for(int i = 0; i != fruits.length; i++){
					if(player.isColliding(fruits[i]) && panel.paintedFruits.get(i).isVisible){
						panel.paintedFruits.get(i).isVisible = false;
						player.score ++;
						panel.repaint();
					}
				}
				
				//Animations
				long temp = System.currentTimeMillis() - player.lastHitTime;
				if((temp <= 200 || (temp >= 400 && temp <= 600) || (temp >= 800 && temp <= 1000))){
					playerImage.setVisible(false);
				}else if(player.lives > 0){
					playerImage.setVisible(true);
				}
				
				
				switch (player.lives) {
				case 1:
					livesImage[1].setVisible(false);
					break;
				case 2:
					livesImage[2].setVisible(false);
					break;
				default:
					break;
				}
				
			}
		});
		t.start();
	}
	
	private void addFruits(){
		int n = 0;
		for(int i = 0; i != 13; i++){
			for(int j = 0; j != 13; j++){
				if(map[i][j] != 1){
					panel.paintedFruits.add(new Fruit(new Double((j*cellSize) + cellSize/2), new Double((i*cellSize + cellSize/2)), 2.0, 2.0));
					fruits[n] = new Collider((j*cellSize) + cellSize/2, (i*cellSize + cellSize/2), 2, 2, 1);
					n++;
				}
			}
		}
	}
	
	private void addWalls(){
		int i = -1;
		do{
			i++;
			//Build walls matrix
			panel.paintedWalls.add(new Wall(walls[i].x, walls[i].y, walls[i].width-1, walls[i].height-1));
			for(int k = 0; k != (walls[i].height/cellSize); k++){
				for(int j = 0; j != (walls[i].width/cellSize); j++){
					map[k + walls[i].y/cellSize][j + walls[i].x/cellSize] = 1;
				}
			}
		}while(i != walls.length-1);
		
		
			
	}
	

	
	public class Panel extends JPanel{
		Graphics g;
		ArrayList<Wall> paintedWalls = new ArrayList<>();
		ArrayList<Fruit> paintedFruits = new ArrayList<>();
		Font font;
		public Panel(){
			setBackground(Color.BLACK);
			//this.font = new Font("TimesRoman", Font.PLAIN, 20);
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setPaint(Color.BLUE);
			
			if(paintedWalls.size() > 0){
				int i = -1;
				do{
					i++;
					g2.fill(paintedWalls.get(i));
					g2.draw(paintedWalls.get(i));
				}while(i != paintedWalls.size() - 1);
			}
			
			g2.setPaint(Color.YELLOW);
			if(paintedFruits.size() > 0){
				int i = -1;
				do{
					i++;
					if(paintedFruits.get(i).isVisible){
						g2.fill(paintedFruits.get(i));
						g2.draw(paintedFruits.get(i));
					}
				}while(i != paintedFruits.size() - 1);
			}
			g2.setFont(g2.getFont().deriveFont(30f)); 
			g2.drawString("Score: " + player.score, 580, 775);
			
		}
	}
	
	private class Listener1 extends KeyAdapter{
		int pressed = 0; //0=none 1=left 2=right 3=up 4=down
		public void keyPressed(KeyEvent e){
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(pressed == 0 && player.direction[0] != -1){
						if(player.direction[0] == 1){
							player.direction[0] = -1;
						}else{
							pressed = 1;
							player.checkAndMove(walls, pressed);
						}
					}
				break;
				
				case KeyEvent.VK_RIGHT:
					if(pressed == 0 && player.direction[0] != 1){
						if(player.direction[0] == -1){
							player.direction[0] = 1;
						}else{
							pressed = 2;
							player.checkAndMove(walls, pressed);
						}
					}
					break;
					
				case KeyEvent.VK_UP:
					if(pressed == 0 && player.direction[1] != -1){
						if(player.direction[1] == 1){
							player.direction[1] = -1;
						}else{
							pressed = 3;
							player.checkAndMove(walls, pressed);
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if(pressed == 0 && player.direction[1] != 1){
						if(player.direction[1] == -1){
							player.direction[1] = 1;
						}else{
							pressed = 4;
							player.checkAndMove(walls, pressed);
						}
					}
					break;
			}
		}
		
		public void keyReleased(KeyEvent e){
			if((e.getKeyCode() == KeyEvent.VK_RIGHT && pressed == 2 )|| (e.getKeyCode() == KeyEvent.VK_LEFT && pressed == 1) || (e.getKeyCode() == KeyEvent.VK_UP && pressed == 3) || (e.getKeyCode() == KeyEvent.VK_DOWN && pressed == 4)){
				pressed = 0;
			}
		}
	}
}
