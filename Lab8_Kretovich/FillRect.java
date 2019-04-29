
import java.awt.*;

public class FillRect extends Canvas{
   
    protected int width;
    protected int height;
    protected Color color;
    
    public FillRect(int width, int height, Color color)
    {
        this.width = width;
        this.height=height;
        this.color = color;
    }
    
    public FillRect( )
    {
        width = 150;
        height=150;
        color = Color.getHSBColor(255,173,255);
    }
    
    public void setWidth(int width)
    {
        this.width = width;
        repaint(); 
    }
    
    public void setHeight(int height)
    {
        this.height = height;
        repaint(); 
    }
    
    public void setColor(Color color)
    {
        //super.setForeground(color);
        this.color = color;
        repaint(); 
    }
    
    @Override
    public int getWidth( )
    {
        return width;
    }
    public int getHeight( )
    {
        return height;
    }
    
    public Color getColor( )
    {
        return color;
    }

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(width, height);
    }
    
    @Override
    public Dimension getMinimumSize() 
    {
        return getPreferredSize(); 
    }
    public Dimension getMaximumSize() 
    {
        return new Dimension(1500, 1500); 
    }
    @Override
    public void paint( Graphics g )
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
    }
}
