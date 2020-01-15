import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow extends JFrame{
	JLabel backgroundLabel;
	JLabel pg;
	int x = 0;
	public MainWindow(){
		super("Pac-Man");
		setSize(900, 960);

		Icon background = new ImageIcon(getClass().getResource("background.png"));
		backgroundLabel = new JLabel(background);
		pg = new JLabel(background);
		add(pg);
		add(backgroundLabel);
		gameCycle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
        setLocationRelativeTo(null);
        setResizable(false);
		setVisible(true);
	}
	
	public void gameCycle(){
		Timer t = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pg.setBounds(x+5, x+5, 50, 50);
				x++;
			}
		});
		t.start();
	}
}
