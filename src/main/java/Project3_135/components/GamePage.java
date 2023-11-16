package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.HookLabel;
import Project3_135.model.ItemLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePage extends BasePage{

    private int itemAmount = 1;
    private int totalScore = 0;
    private int itemCount = 0;
    private HookLabel hookLabel;
    private int[] line = new int[4];


    public GamePage(JPanel cardPanel, CardLayout cardLayout, Color color) {
        super(cardPanel, cardLayout);

        initializeComponents(color);
    }

    @Override
    protected void initializeComponents(Color color) {
        setLayout(new BorderLayout());
        setBackground(color);

        // Create object
        hookLabel = new HookLabel(this,line);

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
        for(int i = 0; i < itemAmount; i++){
            setItemLabel(this);
        }

        // Set SwingWorker for hook
        SwingWorker<Void, Void> hookWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    if (itemCount == itemAmount) {
                        break;
                    }
                    hookLabel.updateLocation();
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
                ItemLabel item = new ItemLabel();

                add(item);
                while (true) {
                    // Kill thread and Collect item
                    if(hookLabel.getOriginalBound().intersects(item.getBounds())){
                        updateScore(item.getScore());
                        itemCount++;
                        break;
                    }

                    // Pull item
                    if (item.getBounds().intersects(hookLabel.getBounds())) {
                        hookLabel.isCatch();
                        item.followHook(hookLabel.getBounds().x, hookLabel.getBounds().y);
                        continue;
                    }

                    item.updateLocation();
                }
                SwingUtilities.invokeLater(() -> {
                    gamePage.remove(item);
                    gamePage.repaint();
                });
            }
        };
        itemThread.start();
    }

    synchronized private void updateScore(int score){
        totalScore += score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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

