import java.awt.*;
import java.applet.*;

@SuppressWarnings("serial")
public class WitchApplet extends Applet {
    private int centerX;
    private int centerY;
    private final int paramA = 100;

    private Shape plot;

    public WitchApplet() {
    }

    protected void update() {
        paint(getGraphics());
    }

    @Override
    public void init() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        plot = new WitchOfAgnesi(paramA, centerX, centerY);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(43, 46, 86));

        g2d.setColor(new Color(34, 84, 150));
        g2d.setStroke(new ToothStroke(5, 5));
        g2d.draw(plot);

    }

} 