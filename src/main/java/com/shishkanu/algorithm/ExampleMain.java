package com.shishkanu.algorithm;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.shishkanu.algorithm.config.Context;
import com.shishkanu.algorithm.domain.FieldDto;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;
import com.shishkanu.algorithm.ui.FieldDrawer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ExampleMain {

    public static void main(String[] args) {
        //Initializing algorithm context
        //most of the config values could be changed through context in runtime
        Context context = Context.getInstance();
        context.init();
        
        //FieldManager - maintains our field
        FieldManager fieldManager = context.getFieldManager();
        
        //Assigning endpoints
        Point start = new Point(2, 3);
        fieldManager.setStart(start);
        Point goal = new Point(95, 80);
        fieldManager.setGoal(goal);
        
        //Change object size (allowed 1, 2, 3)
        //could be also changed through context with same effect
        fieldManager.setObjectSize(2);
        
        //Adding walls
        fieldManager.addWall(new Point(5, 5));
        fieldManager.addWall(new Point(5, 6));
        fieldManager.addWall(new Point(5, 7));
        fieldManager.addWall(new Point(5, 8));
        fieldManager.addWall(new Point(5, 9));
        fieldManager.addWall(new Point(5, 10));
        fieldManager.addWall(new Point(5, 11));
        fieldManager.addWall(new Point(5, 12));
        
        //Update the field and draw path
        fieldManager.updateField();
        
        //Filled field that contains coordintates of drawed endpoints
        //and path
        FieldDto fieldDto = fieldManager.getFieldDto();
        List<Point> path = fieldDto.getPath();
        System.out.printf("path from %s, to %s is: %n%s", start, goal, path);
        
        
        //Fed fieldDto to fieldDrawer to get BufferedImage
        int sizeOfOnePixel = 4;                                     //4pixels- size of one square on the grid
        FieldDrawer fieldDrawer = new FieldDrawer(sizeOfOnePixel);
        BufferedImage bufferedImage = fieldDrawer.fieldToImage(fieldDto);
        
        displayImage(bufferedImage);
            
    }
    
    public static void displayImage(BufferedImage bufferedImage) {
        ImageIcon icon = new ImageIcon(bufferedImage);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(640, 480);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
