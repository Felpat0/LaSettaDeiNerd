import javax.swing.JFrame;

public class MainWindow extends JFrame{
	public MainWindow(){
		super("Pac-Man");
		setSize(900, 960);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		setVisible(true);
	}
}
