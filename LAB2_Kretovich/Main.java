import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Main {


    private static int width = 900;
    private static int height = 600;

    public static void main(String[] args) {
        Drawing dr=new Drawing();
        JFrame frame = new JFrame();
        frame.setSize(width, height);

        final JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paint(Graphics g) {
                dr.paint(g);
            }
        };


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}