package Project3_135.model;

import javax.swing.*;
import java.awt.*;

public class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fileName)  { super(fileName); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int percent) {
        Image oldImg = this.getImage();

        // Calculate new dimensions based on the percentage
        int newWidth = (int) (oldImg.getWidth(null) * percent / 100.0);
        int newHeight = (int) (oldImg.getHeight(null) * percent / 100.0);

        Image newImg = oldImg.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newImg);
    }
}
