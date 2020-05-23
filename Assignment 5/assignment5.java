/*
 *    Assignment 5
	Fetching images from link and convert them into grayscale using single threading and multithreading
* 
*  Fall 2019
* Course: Concurrent Programming: Theory and Practice
* Professor: Patrick McKee
* Author: Kriyanshi Patel
 * 
 * 
 */

import java.awt.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Assignment5 {

	
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static AtomicInteger imageCounter = new AtomicInteger(1);

    public static void main(String[] args) throws IOException {

        int number = 0;
		
        while (number != 3) {
            
               System.out.println();
        System.out.println("Please select from menu");      
        System.out.println("1. Single Thread");
        System.out.println("2. Multi Thread");
        System.out.println("3. Exit");
        System.out.println();
        

         number = Integer.parseInt(br.readLine());

            switch (number) {
            case 1: { 
              
                System.out.println("Single Threading....");

				List<String> fetchimgs = getImages();

        for (int i = 0; i < fetchimgs.size(); i++) {
            BufferedImage greyImage = greyScale(readImage(fetchimgs.get(i)));
			String imgname= imageCounter.getAndIncrement()  + ".jpg";
			String imgpath="images/SingleThread/greyimage";
            String imgOutput = imgpath + imgname;

			ImageIO.write(greyImage, "jpg", new File(
                                imgOutput ));
            
        }
                System.out.println("Images converted");
                break;
            }
            case 2: { 

                
                System.out.println("Multi Threading....");


        getImages().stream().parallel().map(url -> FinalExam.readImage(url))
                .map(Image -> FinalExam.greyScale(Image)).forEach(greyImage -> {
                    try {
						
						String imgname= imageCounter.getAndIncrement() + ".jpg";
						String imgpath= "images/MultiThread/greyimage";
						String imgOutput = imgpath + imgname;
                        ImageIO.write(greyImage, "jpg", new File(
                                imgOutput ));

                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });



                System.out.println("Images are converted");
                break;
            }
            case 3: { 
                
                System.out.println("Thank you!");
				System.out.println("Good Bye");
               

                break;
            }
            default: {
                System.out.println();
                System.out.println("Invalid input.");
                System.out.println();

                break;
            }
            }
        } 

    } 

    
	  private static BufferedImage readImage(String url) {
        try {
            return ImageIO.read(new URL(url).openStream());
			
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     private static List<String> getImages() throws IOException {
        List<String> urls = new ArrayList<String>();
        URL elvisurl = new URL(" <link>");

        BufferedReader in = new BufferedReader(new InputStreamReader(elvisurl.openStream()));

        String inputLine;
        String link;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains(".jpg")) {
                link = elvisurl.toString()
                        + inputLine.substring((inputLine.indexOf("href=\"") + 6), (inputLine.indexOf(".jpg") + 4));

                urls.add(link);
            }
        }

        return urls;
    }
   
    private static BufferedImage greyScale(BufferedImage image) {
        int imgwidth = image.getWidth();
        int imgheight = image.getHeight();

        

        for (int row = 0; row < imgwidth; row++) {
            for (int col = 0; col < imgheight; col++) {
                Color pixelValues = new Color(image.getRGB(row, col));

               int r = pixelValues.getRed();
               int g = pixelValues.getGreen();
               int b = pixelValues.getBlue();

               int avg = (r + g + b) / 3;

                Color newPixelColors = new Color(avg, avg, avg);

                image.setRGB(row, col, newPixelColors.getRGB());
            }
        }

        return image;
    }

   
} 

/*
References:
https://stackoverflow.com/questions/7662180/reading-data-from-a-url-and-putting-it-into-an-arraylist

https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm

*/