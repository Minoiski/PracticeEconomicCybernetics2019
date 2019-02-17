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
    private int [] xPoints = {200, 250, 400};
    private int [] yPoints = {200, 114, 250};
    private int n = 3;
    private Color borderColor = Color.green;
    private Color backgroundColor = Color.red;
    private MyTriangle myTriangle;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double theta = 0;
    private static final float BORDER = 3;

    private Timer timer;

    private final static double ROTATE_ANGLE = Math.PI / 180;

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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(BORDER));
        myTriangle = new MyTriangle(borderColor, backgroundColor, theta, xPoints, yPoints, n);
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
