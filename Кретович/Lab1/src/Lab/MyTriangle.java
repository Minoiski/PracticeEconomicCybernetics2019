package Lab;
import java.awt.*;
import java.awt.geom.*;

public class MyTriangle extends Canvas implements Shape {

    private int[]xPoints;
    private int[]yPoints;
    private int n;
    private int centerX;
    private int centerY;
    private Color borderColor;
    private Color backgroundColor;
    private double theta;
    
    public MyTriangle(Color clr1, Color clr2, double theta, int [] xPoints, int [] yPoints, int numberOfPoints)
    {
        this.xPoints=xPoints;
        this.yPoints=yPoints;
        this.n=numberOfPoints;
        this.theta=theta;
        backgroundColor=clr2;
        borderColor=clr1;
        this.centerX = (xPoints[0] + xPoints[1] + xPoints[2]) / 3;
        this.centerY = (yPoints[0] + yPoints[1] + yPoints[2]) / 3;
    }
    
    public void setTheta(double theta)
    {
        this.theta=theta;
    }
    
    @Override
    public void paint(Graphics graphics)
    {
        Graphics2D graphics2D = (Graphics2D) graphics;
        AffineTransform affineTransform = AffineTransform.getRotateInstance(this.theta, this.centerX, this.centerY);
        graphics2D.setTransform(affineTransform);
        graphics2D.setColor(backgroundColor);
        graphics2D.fillPolygon(xPoints, yPoints,n);
        graphics2D.setColor(borderColor);
        graphics2D.drawPolygon(xPoints, yPoints,n);
    }
    
    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return null;
    }
}
