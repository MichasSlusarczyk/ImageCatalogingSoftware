package pl.polsl.models;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import pl.polsl.models.hashcode.*;

public class Sorter {
    
    ArrayList<HashedImage> hashedImages;
    ArrayList<ArrayList<String>> sortedImages;
    double similarityThreshold;
    
    public Sorter()
    {
        hashedImages = new ArrayList<>();
        sortedImages = new ArrayList<>();
        similarityThreshold = 0.75;
    }
    
    public Sorter(double treshold)
    {
        hashedImages = new ArrayList<>();
        sortedImages = new ArrayList<>();
        similarityThreshold = treshold;
    }
    
    public void sort(ArrayList<String> imagePaths)
    {
        generateHashcodes(imagePaths);
           
        while (!hashedImages.isEmpty())
        {
            String firstImageHash = hashedImages.get(0).hash;
            ArrayList<String> virtualFolder = new ArrayList<>();
            int unsortedImagesCount = hashedImages.size();
            
            for(int i=0; i< unsortedImagesCount; i++)
            {
                if(compareHashCodes(firstImageHash, hashedImages.get(i).hash) >= similarityThreshold)
               {
                  virtualFolder.add(hashedImages.get(i).path);
                  hashedImages.get(i).hash="0";
                }
            }
            
            hashedImages.removeIf( hi -> hi.hash.equals("0"));       

            sortedImages.add(virtualFolder);
        }
    }
    
    private void generateHashcodes(ArrayList<String> imagePaths)
    {
        PerceptualImageHash pih = new PHashImageHash();
        
        for(int i=0; i < imagePaths.size(); i++)
        {
            HashedImage singleImage = new HashedImage();
            singleImage.path = imagePaths.get(i);
            Image img = getImage(singleImage.path);
            singleImage.hash = Long.toBinaryString(pih.getPerceptualHash(img));
            
            hashedImages.add(singleImage);
        }
    }
    
    private BufferedImage getImage(String path)
    {
        BufferedImage tempImg;
        try 
        {
            tempImg = ImageIO.read(new File(path));
            return tempImg;
        } 
        catch (IOException e) 
        {
        }
        
        return null;
    }
    
    private double compareHashCodes(String firstImageHash, String secondImageHash)
    {
        int counter = firstImageHash.length();
        for(int i = 0; i < firstImageHash.length(); i++)
        {
           if(firstImageHash.charAt(i) !=  secondImageHash.charAt(i))
           {
               counter -= 1;
           }
        }
        
        return ((double)counter/64);
    }
}

class HashedImage
{
    String hash;
    String path;
}

