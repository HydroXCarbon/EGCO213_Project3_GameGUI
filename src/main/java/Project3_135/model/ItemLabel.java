package Project3_135.model;

import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import java.awt.*;

public class ItemLabel extends JLabel {
    public final int type;
    private final MyImageIcon itemImg;
    String[] imageFiles = {Utilities.DIAMOND_IMAGE_PATH, Utilities.ROCK_SMALL_IMAGE_PATH, Utilities.ROCK_LARGE_IMAGE_PATH};
    private int curX;
    private int curY;
    private int width;
    private int height;

    public ItemLabel() {
        curX = (int) (Math.random() * 5555) % (Utilities.FRAMEWIDTH - 100);
        curY = 500;
        type = (int) (Math.random() * 3);

        itemImg = new MyImageIcon(imageFiles[type]).resize(8);
        width = itemImg.getIconWidth();
        height = itemImg.getIconHeight();
        setIcon(itemImg);
        setBounds(curX, curY, width, height);

    }

    public void updateLocation() {

        setLocation(curX, curY);
        setBounds(curX, curY, width, height);
        repaint();
        validate();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void followHook(int x, int y){
        curX = x;
        curY = y;
        updateLocation();
    }
}
