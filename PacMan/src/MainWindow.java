import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class MainWindow extends JFrame{
	JLabel backgroundLabel;
	JLabel playerImage;
	Player player;
	Collider walls[] = {new Collider(721, 612, 106, 29) {}
	
	};
	Icon playerSprites[] = {new ImageIcon(getClass().getResource("player.png"))};
	Icon background = new ImageIcon(getClass().getResource("background.png"));
	
	public MainWindow(){
		super("Pac-Man");
		setSize(900, 960);
		player = new Player((this.getWidth()/2)-31, 632, 60, 60, 3, playerSprites);

		backgroundLabel = new JLabel(background);
		playerImage = new JLabel(player.sprites[0]);
		
		add(playerImage);
		add(backgroundLabel);
		
		playerImage.setBounds(player.x, player.y, player.height, player.width);
		addWalls();
		gameCycle();
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
				player.move();
				if(player.isColliding(walls[0])){
					player.direction[0] = 0;
					player.direction[1] = 0;
					
					player.x --;
				}
				playerImage.setBounds(player.x, player.y, player.height, player.width);
			}
		});
		t.start();
	}
	
	public void addWalls(){
		
	}
	
	private class Listener1 extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					player.direction[0] = 1;
					player.direction[1] = 0;
					break;
				case KeyEvent.VK_LEFT:
					player.direction[0] = -1;
					player.direction[1] = 0;
					break;
				case KeyEvent.VK_UP:
					player.direction[0] = 0;
					player.direction[1] = -1;
					break;
				case KeyEvent.VK_DOWN:
					player.direction[0] = 0;
					player.direction[1] = 1;
					break;
			}
		}
		
		public void keyReleased(KeyEvent e){
				
		}
	}
}
