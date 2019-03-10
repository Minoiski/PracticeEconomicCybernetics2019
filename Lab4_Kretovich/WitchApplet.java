import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("serial")
public class WitchApplet extends JApplet {

    JButton jb = new JButton("Print");
    private int centerX;
    private int centerY;
    private final int paramA = 100;

    private Shape plot, plot1;

    public WitchApplet() {
    }

    protected void update() {
        paint(getGraphics());
    }

    @Override
    public void init() {
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                printRequestAttributeSet.add(Sides.DUPLEX);//TWO_SIDED_SHORT_EDGE);//DUPLEX);
                printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);

                job.setPrintable(new MyPrintable(), pf);
                boolean ok = job.printDialog(printRequestAttributeSet);
                if (ok) {
                    try {
                        job.print(printRequestAttributeSet);
                    } catch (PrinterException ex) {
                        System.err.print(ex);
                    }
                }
            }
        });
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(jb);
        centerX = getWidth();
        centerY = getHeight();
        plot = new WitchOfAgnesi(paramA, centerX, centerY);
        this.setSize(getWidth() * 2, getHeight() * 2);
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


    class MyPrintable implements Printable {
        public int print(Graphics g, PageFormat pf, int pageIndex) {
            if (pageIndex != 0)
                return NO_SUCH_PAGE;
            int a = 100, b = (int) pf.getImageableHeight() * 3 / 4;
            Graphics2D g2d = (Graphics2D) g;
            String c;
            g2d.setFont(new Font("Serif", Font.PLAIN, 26));
            g2d.drawString("Линия: Каппа", (int) (pf.getImageableWidth() / 2), 100);
            Rectangle2D outline = new Rectangle2D.Double(0, 0, pf.getImageableWidth() * 2, pf.getImageableHeight() * 2);
            g2d.setFont(new Font("Serif", Font.PLAIN, 5));
            try {
                BufferedReader f = new BufferedReader(new FileReader("C:\\Users\\Евгений\\IdeaProjects\\УП_Лаб4\\src\\WitchOfAgnesi.java"));
                while ((c = f.readLine()) != null) {

                    g2d.drawString(c, a, b);
                    b += 5;
                    if (b > pf.getImageableHeight() * 5 / 4 - 40) {
                        b = (int) pf.getImageableHeight() * 3 / 4;
                        a += 150;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            plot1 = new WitchOfAgnesi(paramA, (int) pf.getImageableWidth() / 2, (int) pf.getImageableHeight() / 2);
            g2d.draw(outline);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(43, 46, 86));

            g2d.setColor(new Color(34, 84, 150));
            g2d.setStroke(new ToothStroke(5, 5));
            g2d.draw(plot1);
            return PAGE_EXISTS;
        }
    }
} 