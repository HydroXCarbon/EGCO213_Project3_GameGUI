package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.HookLabel;
import Project3_135.model.ItemLabel;
import Project3_135.model.MyImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePage extends BasePage {

    private final int itemAmount = Utilities.ITEMAMOUNT;
    private final int[] line = new int[4];
    private int totalScore = 0;
    private int itemCount = 0;
    private HookLabel hookLabel;
    private Image backgroundImage;
    private final List<ItemLabel> itemList = new CopyOnWriteArrayList<>();


    public GamePage(JPanel cardPanel, CardLayout cardLayout) {
        super(cardPanel, cardLayout);

        initializeComponents();
    }

    protected void initializeComponents() {
        setLayout(new BorderLayout());

        // Create background
        backgroundImage = new MyImageIcon(Utilities.BACKGROUND_IMAGE_PATH).getImage();

        // Create object
        hookLabel = new HookLabel(this, line);

        // Set initial location for hook
        hookLabel.setInitial(Utilities.FRAMEWIDTH / 2, 150);

        // Add components

        add(hookLabel);

        // Add key listener
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    hookLabel.setMove();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // Set Thread for item
        for (int i = 0; i < itemAmount; i++) {
            setItemLabel(this);
        }

        // Set SwingWorker for hook
        SwingWorker<Void, Void> hookWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    hookLabel.updateLocation();
                    if (itemCount == itemAmount) {
                        break;
                    }
                    publish();
                    repaint();
                }
                return null;
            }

            @Override
            protected void done() {
                cardLayout.show(cardPanel, "mainPage");
                repaint(); // Ensure final repaint on the main UI thread
            }
        };

        hookWorker.execute(); // Start the SwingWorker


    }

    private void setItemLabel(GamePage gamePage) {
        Thread itemThread = new Thread() {
            public void run() {
                boolean firstHit = false;
                final ItemLabel[] item = {null};
                // Create a new item
                createNewItem(item);
                add(item[0]);
                //SwingUtilities.invokeLater(() -> gamePage.add(item[0]));

                while (true) {

                    // Kill thread and collect item
                    if (hookLabel.getOriginalBound().intersects(item[0].getBounds())) {
                        updateScore(item[0].getScore());
                        itemCount++;
                        break;
                    }

                    // Pull item
                    if (item[0].getBounds().intersects(hookLabel.getBounds())) {
                        if (!firstHit) {
                            item[0].playHitSound();
                            firstHit = true;
                        }
                        hookLabel.isCatch();
                        hookLabel.setSpeed(item[0].getSpeedPenalty());
                        item[0].followHook(hookLabel.getBounds().x, hookLabel.getBounds().y);
                        continue;
                    }
                    item[0].updateLocation();
                }

                hookLabel.setSpeed(100);
                SwingUtilities.invokeLater(() -> {
                    gamePage.remove(item[0]);
                    gamePage.repaint();
                });
            }
        };
        itemThread.start();
    }

    private void createNewItem(ItemLabel[] item) {
        do {
            item[0] = new ItemLabel();
        } while (isOverlapping(item[0]));

        itemList.add(item[0]);
    }

    private boolean isOverlapping(ItemLabel newItem) {
        synchronized (itemList) {
            for (ItemLabel existingItem : itemList) {
                if (existingItem.getBounds().intersects(newItem.getBounds())) {
                    return true;
                }
            }
            return false;
        }
    }


    synchronized private void updateScore(int score) {
        totalScore += score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Set the stroke (line width)
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3.0f));

        // Draw the line
        g2d.drawLine(line[0], line[1], line[2], line[3]);
        //g2d.drawLine(0, 0, 2000, 500);

        // Restore the original stroke
        g2d.setStroke(oldStroke);
    }

}

