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
        Pair dim = getDimensions();
        g.drawImage(image, dim.x, dim.y, dim.width, dim.height, null);            
    }

    private Pair getDimensions()
    {
        Pair res = new Pair(0, 0, 0, 0);
        if(image == null) return res;

        double iw = image.getWidth();
        double ih = image.getHeight();

        double tw = this.getWidth();
        double th = this.getHeight();

        if((ih/iw)*tw <= th)
        {
            System.out.println("Widther");
            res.width = this.getWidth();
            res.height = (int) Math.round((ih/iw) * tw);
            res.x = 0;
            res.y = (int) ((th / 2) - (((double)res.height) / 2)); 
            return res;
        }
        
        System.out.println("Heighter");
        res.height = this.getHeight();
        res.width = (int) Math.round((iw/ih) * th);
        res.y = 0;
        res.x = (int) ((tw / 2) - (((double)res.width) / 2));

        return res;
    }

    private class Pair 
    {
        public Pair(int a, int b, int c, int d)
        {
            width = a;
            height = b;
            x = c;
            y = d;
        }
        public int width;
        public int height;
        public int x;
        public int y;
    }
}
