package Lab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Main extends JComponent implements ActionListener
{
    private static final int [] xPoints = new int [] {200, 250, 400};
    private static final int [] yPoints = new int [] {200, 114, 250};
    private int n = 3;
    private Color borderColor = Color.green;
    private Color backgroundColor = Color.red; 
    
    private double theta = 0;
    private static final int WIDTH = 800, HEIGHT = 600;
    private static final float BORDER = 3;
    private final static double ROTATE_ANGLE = Math.PI / 180;
    private MyTriangle myTriangle = new MyTriangle(borderColor, backgroundColor, theta, xPoints, yPoints, n);
    
    private Timer timer;

    private Main(int delay)
    {
        timer = new Timer(delay, this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void start()
    {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        myTriangle.setTheta(theta);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(BORDER));
        myTriangle.paint(graphics2D);
        theta += ROTATE_ANGLE;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Triangle");
            JPanel panel = new JPanel();
            Main movingTriangle = new Main(5);
            panel.add(movingTriangle);
            frame.getContentPane().add(panel);
            movingTriangle.start();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
        });
    }

}
