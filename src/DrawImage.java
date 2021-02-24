import javax.swing.*;
import java.awt.*;

public class DrawImage extends JPanel{
	private Image image = null;
	public DrawImage(Image image) {
		this.image = image;
	}
	public DrawImage() {
		this.setBackground(Color.WHITE);
	}
    public void paintComponent(Graphics g) {
    	g.drawImage(image, 0, 0, 50, 50, this); 
    }
}
