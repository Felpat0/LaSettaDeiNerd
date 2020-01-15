import javax.swing.*;

public class MainWindow extends JFrame{
	JLabel backgroundLabel;
	public MainWindow(){
		super("Pac-Man");
		setSize(900, 960);
		
		Icon background = new ImageIcon(getClass().getResource("background.png"));
		
		backgroundLabel = new JLabel(background);
		add(backgroundLabel);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		setVisible(true);
	}
}
