package Project3_135.model;

import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class HookLabel extends JLabel {

    private GamePage gamePage;
    private final Icon hookIcon;
    private Rectangle originalBound;
    private int[] line;
    private int curY = 0;
    private int curX = 0;
    private int originalSpeed = 1;
    private int speed;
    private final int originalRadius = 100;
    private int radius;
    private double angle = Math.toRadians(270);
    private double angularSpeed = Math.toRadians(0.1);
    private boolean rotateRight = true;
    private boolean move = false;
    private boolean moveDown = true;

    public HookLabel(GamePage gamePage,int[] line) {
        // Load the image icons
        ImageIcon hookIcon = new MyImageIcon(Utilities.HOOK_IMAGE_PATH).resize(20);
        setIcon(hookIcon);

        this.gamePage = gamePage;
        this.hookIcon = hookIcon;
        this.line = line;
        this.radius = this.originalRadius;
        this.speed = this.originalSpeed;
    }

    public void setInitial(int x, int y){
        curX = x - hookIcon.getIconWidth() / 2;
        curY = y - hookIcon.getIconWidth() / 2;
        int newX = curX + (int) (radius * Math.cos(angle));
        int newY = curY - (int) (radius * Math.sin(angle));
        setBounds(curX, curY, hookIcon.getIconWidth(), hookIcon.getIconHeight());
        originalBound = new Rectangle(
                newX - hookIcon.getIconWidth()*2,
                newY - hookIcon.getIconHeight() *3/2,
                hookIcon.getIconWidth()*5,
                hookIcon.getIconHeight()*5/2);
//        line[0] = newX - hookIcon.getIconWidth()*2;
//        line[1] = newY - hookIcon.getIconHeight() *3/2;
//        line[2] = newX - hookIcon.getIconWidth()*2     + hookIcon.getIconWidth()*5;
//        line[3] = newY - hookIcon.getIconHeight() *3/2 + hookIcon.getIconHeight()*5/2;
    }
    public Rectangle getOriginalBound(){
        return this.originalBound;
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public void setMove() {
        move = true;
    }

    public void isCatch(){
        if(!move){
            moveDown = true;
        }else {
            moveDown = false;
        }
    }

    public void updateLocation() {

        int newX = curX + (int) (radius * Math.cos(angle));
        int newY = curY - (int) (radius * Math.sin(angle));

        line[0] = curX + hookIcon.getIconWidth() / 2;
        line[1] = curY + hookIcon.getIconHeight() / 2;
        line[2] = newX + hookIcon.getIconWidth() / 2;
        line[3] = newY + hookIcon.getIconHeight() / 2;

        // Update the angle direction based on the desired pattern
        if (angle + angularSpeed >= Math.toRadians(350) && rotateRight) {
            rotateRight = false;
        } else if (angle - angularSpeed <= Math.toRadians(190) && !rotateRight) {
            rotateRight = true;
        }

        // Create a rotated copy of the original icon
        ImageIcon rotatedIcon = rotateIcon(hookIcon, angle);

        // Set the rotated icon
        setIcon(rotatedIcon);

        // Update the angle value
        if (!move) {
            if (rotateRight) {
                angle += angularSpeed;
            } else {
                angle -= angularSpeed;
            }
        }else{
            // Check out of frame
            if(newX < 0 || newX + hookIcon.getIconWidth() > Utilities.FRAMEWIDTH || newY + hookIcon.getIconHeight() > Utilities.FRAMEHEIGHT) {
                moveDown = false;
            }

            if(moveDown) {
                radius += speed;
            }else{
                if(radius < originalRadius){
                    radius = originalRadius;
                    move = false;
                    moveDown = true;
                }else{
                    radius -= speed;
                }
            }
        }

        // Set new position
        setLocation(newX, newY);
        setBounds(newX, newY, hookIcon.getIconWidth(), hookIcon.getIconHeight());

        // Set FPS
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon rotateIcon(Icon icon, double angle) {
        // Calculate the new image size based on the angle
        // Change the constant values if rotate image is bug
        int newWidth  = (int) (icon.getIconWidth() * 1);
        int newHeight = (int) (icon.getIconHeight() * 1);

        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set the rotation angle for the graphics context
        AffineTransform rotationTransform = AffineTransform.getRotateInstance(-(angle+Math.toRadians(90)), icon.getIconWidth() / 2.0, icon.getIconHeight() / 2.0);
        g2d.setTransform(rotationTransform);

        // Calculate the position to center the rotated image
        int x = (newWidth - icon.getIconWidth()) / 2;
        int y = (newHeight - icon.getIconHeight()) / 2;

        // Draw the rotated image
        icon.paintIcon(this, g2d, x, y);
        g2d.dispose();

        return new ImageIcon(image);
    }
}

