// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 112 Assignment
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;


/** ImageProcessor allows the user to load, display, modify, and save an image in a number of ways.
 *  The program should include
 *  - Load, commit, save. (Core)
 *  - Brightness adjustment (Core)
 *  - Horizontal flip and 90 degree rotation. (Core)
 *  - Merge  (Core)
 *  - Crop&Zoom  (Core)
 *  - Blur (3x3 filter)  (Core)
 * 
 *  - Rotate arbitrary angle (Completion)
 *  - Pour (spread-fill)  (Completion)
 *  - General Convolution Filter  (Completion)
 * 
 *  - Red-eye detection and removal (Challenge)
 *  - Filter brush (Challenge)
 */
public class ImageProcessor {
    /*# YOUR CODE HERE */
    public ImageProcessor() {
        UI.initialise();
        UI.addButton("Load File", this::loadImage);
        UI.addButton("Quit", UI::quit);
    }
    
    public Color[][] loadImage() {
        String imageName = UIFileChooser.open();
        if (imageName==null) return;
        try {
            BufferedImage img = ImageIO.read(new File(imageName));
            int rows = img.getHeight();
            int cols = img.getWidth();
            Color[][] ans = new Color[rows][cols];
            for (int row = 0; row < rows; row++){
                for (int col = 0; col < cols; col++){                
                    Color c = new Color(img.getRGB(col, row));
                    ans[row][col] = c;
                }
            }
            UI.printMessage("Loaded "+ imageName);
            return ans;
        } catch(IOException e){UI.println("Image reading failed: "+e);}
    }
    
    /**public  void saveImage() {
        int rows = image.length;
        int cols = image[0].length;
        BufferedImage img = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Color c =this.image[row][col];
                img.setRGB(col, row, c.getRGB());
            }
        }
        try {
            String fname = UIFileChooser.save("save to png image file");
            if (fname==null) return;
            File imageFile = new File(fname);
            ImageIO.write(img, "png", new File(fname));
        } catch(IOException e){UI.println("Image reading failed: "+e);}
    }*/


}
