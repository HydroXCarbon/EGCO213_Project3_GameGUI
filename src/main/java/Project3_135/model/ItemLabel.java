package Project3_135.model;

import Project3_135.Utilities;

import javax.swing.*;

public class ItemLabel extends JLabel {

    private int type = 0;
    private final MyImageIcon itemImg;
    private final int width;
    private final int height;
    private final MySoundEffect hitSound;
    private final int score;
    private final int speedPenalty;
    String[] imageFiles;
    private double[] spawnChance;
    double randomValue = Math.random();
    double cumulativeProbability = 0.0;
    private int curX;
    private int curY;
    private int[] value;

    public ItemLabel() {
        imageFiles = Utilities.IMAGEFILES;
        spawnChance = Utilities.SPAWNCHANCE;

        // Randomly choose item type based on spawnChance
        for (int i = 0; i < spawnChance.length; i++) {
            cumulativeProbability += spawnChance[i] / 100;
            if (randomValue < cumulativeProbability) {
                type = i;
                break;
            }
        }

        switch (type) {
            case 0:
                value = Utilities.SPAWNCONDITION1;
                break;
            case 1:
                value = Utilities.SPAWNCONDITION2;
                break;
            case 2:
                value = Utilities.SPAWNCONDITION3;
                break;
            case 3:
                value = Utilities.SPAWNCONDITION4;
                break;
            case 4:
                value = Utilities.SPAWNCONDITION5;
                break;
        }

        score = value[2];
        speedPenalty = value[3];
        curX = (int) (Math.random() * (Utilities.FRAMEWIDTH - 100));
        curY = (int) (Math.random() * (value[1] - value[0]) + value[0]);

        hitSound = new MySoundEffect(Utilities.ROCKHIT_SOUND_PATH);
        hitSound.setVolume(0.5f);
        itemImg = new MyImageIcon(imageFiles[type]).resize(9);
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

    public int getScore() {
        return score;
    }

    public int getSpeedPenalty() {
        return speedPenalty;
    }

    public void followHook(int x, int y) {
        curX = x;
        curY = y;
        updateLocation();
    }

    public void playHitSound() {
        hitSound.playOnce();
    }
}
