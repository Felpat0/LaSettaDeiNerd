import javax.swing.*;

public class MainWindow extends JFrame{
	JLabel backgroundLabel;
	JPanel main;
	int x = 0;
	public MainWindow(){
		super("Pac-Man");
		setSize(900, 960);
		
		Icon background = new ImageIcon(getClass().getResource("background.png"));
		backgroundLabel = new JLabel(background);
		main = new JPanel();
		main.add(backgroundLabel);
		add(main);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
		setVisible(true);
		gameCycle();
	}
	
	public void gameCycle(){
		backgroundLabel.setBounds(x+1, x+1, 50, 50);
	}
}
