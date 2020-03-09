import java.awt.geom.Ellipse2D;

public class Fruit extends Ellipse2D.Double {
	Boolean isVisible;
	public Fruit(double x, double y, double w, double h) {
		super(x, y, w, h);
		this.isVisible = true;
	}
}
