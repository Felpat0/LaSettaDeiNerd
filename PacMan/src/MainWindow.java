import java.awt.Color;
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
 * - Fix enemies (tunnel)
 * - Fix player (when pressing button too close to a wall)
 * - Add healthbar
 * - Add fruits where there are no walls
 */


public class MainWindow extends JFrame{
	Panel panel;
	JLabel playerImage;
	JLabel[] enemyImage;
	Player player;
	Enemy[] enemy;
	ArrayList<Wall> points = new ArrayList<>();
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
	Icon playerSprites[] = {new ImageIcon(getClass().getResource("player.png"))};
	Icon enemySprites[] = {new ImageIcon(getClass().getResource("ghost.png"))};
	
	public MainWindow(){
		super("Pac-Man");
		//Game size: 728x728
		setSize(728+6, 728+29);
		panel = new Panel();
		
		addWalls();
		
		player = new Player(1, 3, 1, 1, 4, playerSprites);
		enemy = new Enemy[3];
		enemy[0] = new Enemy(5, 1, 1, 1, 4, enemySprites);
		enemy[1] = new Enemy(6, 1, 1, 1, 4, enemySprites);
		enemy[2] = new Enemy(7, 1, 1, 1, 4, enemySprites);

		playerImage = new JLabel(player.sprites[0]);
		enemyImage = new JLabel[3];
		enemyImage[0] = new JLabel(enemy[0].sprites[0]);
		enemyImage[1] = new JLabel(enemy[1].sprites[0]);
		enemyImage[2] = new JLabel(enemy[2].sprites[0]);
		
		add(playerImage);
		for(int i = 0; i != enemy.length; i++){
			add(enemyImage[i]);
			enemyImage[i].setBounds(enemy[i].x, enemy[i].y, enemy[i].height, enemy[i].width);
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
					enemyImage[i].setBounds(enemy[i].x, enemy[i].y, enemy[i].height, enemy[i].width);
					if(player.isColliding(enemy[i]) && playerImage.isVisible()){
						
						if(System.currentTimeMillis() - player.lastHitTime > player.invincibilityTime){
							player.lastHitTime = System.currentTimeMillis();
							player.lives--;
							if(player.lives == 0)
								playerImage.setVisible(false);
						}
					}
				}
				
				
				
			}
		});
		t.start();
	}
	
	public void addWalls(){
		int i = -1;
		do{
			i++;
			panel.paintedWalls.add(new Wall(walls[i].x, walls[i].y, walls[i].width-1, walls[i].height-1));
		}while(i != walls.length-1);
	}
	

	
	public class Panel extends JPanel{
		Graphics g;
		ArrayList<Wall> paintedWalls = new ArrayList<>();
		ArrayList<Wall> points = new ArrayList<>();
		public Panel(){
			setBackground(Color.BLACK);
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
			
			g2.setPaint(Color.BLACK);
			
			if(points.size() > 0){
				int i = -1;
				do{
					i++;
					g2.fill(points.get(i));
					g2.draw(points.get(i));
				}while(i != points.size() - 1);
			}
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
