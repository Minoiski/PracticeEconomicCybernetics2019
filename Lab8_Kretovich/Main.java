
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;


public class Main extends Applet{
	
	public void paint(Graphics g)
	{
		FillRect fl = new FillRect(50, 50, Color.green);
		fl.paint(g);
	}


}
