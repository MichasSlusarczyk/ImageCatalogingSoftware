package pl.polsl.views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;

public class ImagePanel extends JPanel
{
    private BufferedImage image;

    public void clear()
    {
        image = null;
    }

    public void setImage(String imagePath)
    {
        try 
        {                
            image = ImageIO.read(new File(imagePath));
        } 
        catch (IOException ex) 
        {
            System.out.println("Image cannot be opened: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);            
    }
}
