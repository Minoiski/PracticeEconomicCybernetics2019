import java.awt.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Drawing extends Canvas {
    private Color bgr;
    private Color col;
    private Color fillCol1;
    private Color fillCol2;
    private final int Width = 400;
    private final int Height = 300;
    private final int X = Width / 8;
    private final int Y = Height / 8;
    private final int side = Height / 2;
    private Square sq = new Square(X, Y, side);

    Drawing() {
        super();
        bgr = Color.gray;
        fillCol1 = Color.lightGray;
        fillCol2 = Color.darkGray;
        col = Color.red;
        Dimension dim = new Dimension(getWidth(), getHeight());
        setMaximumSize(dim);
        setBounds(0, 0, dim.width, dim.height);
    }

    public void paint(Graphics g) {
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(bgr);
        Graphics2D g2 = (Graphics2D) g;
        g.fillRect(0, 0, getWidth(), getHeight());
        BufferedImage original = initOr();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AffineTransform at = new AffineTransform();
        at.translate(500, 200);
        at.rotate(-Math.PI / 4);
        g2.drawImage(original, 0, 0, null);
        g2.drawImage(original, at, null);
    }

    private BufferedImage initOr() {
        BufferedImage bufferedImage = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Paint shadowPaint = new Color(0, 0, 0, 90);
        AffineTransform shadowTransform = AffineTransform.getShearInstance(-2.71, 0);
        shadowTransform.scale(1.0, 0.48);
        g2.setPaint(shadowPaint);
        g2.translate(250, 100);
        g2.fill(shadowTransform.createTransformedShape(sq));
        g2.translate(-250, -100);
        g2.setPaint(new GradientPaint(X , Y , fillCol1, X, Y+ side, fillCol2));
        g2.fill(sq);
        g2.setColor(col);
        int board = 10;
        g2.setStroke(new BasicStroke(board));
        g2.draw(sq);
        Font font = new Font("TimesRoman", Font.PLAIN, 6);
        Font affineFont = font.deriveFont(AffineTransform.getScaleInstance((double) 6, 6));
        GlyphVector gv = affineFont.createGlyphVector(g2.getFontRenderContext(), "STOP");
        g2.translate(40, 60);
        g2.drawGlyphVector(gv, 40, 60);
        return bufferedImage;
    }
}