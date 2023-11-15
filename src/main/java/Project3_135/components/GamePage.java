package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.HookLabel;
import Project3_135.model.ItemLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePage extends BasePage {

    private HookLabel hookLabel;

    public GamePage(JPanel cardPanel, CardLayout cardLayout, Color color) {
        super(cardPanel, cardLayout);

        initializeComponents(color);
    }

    @Override
    protected void initializeComponents(Color color) {
        setLayout(new BorderLayout());
        setBackground(color);

        // Create object
        hookLabel = new HookLabel();

        // Add components
        add(hookLabel);

        // Set initial location for hook
        hookLabel.setInitial(Utilities.FRAMEWIDTH / 2, 150);

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

        startGame();
    }

    private void startGame() {

        // Set Thread for item
        for(int i = 0; i < 10; i++){
            setItemLabel(this);
        }

        // Set Thread for hook
        setHookThread(hookLabel);
    }

    private void setHookThread(HookLabel hookLabel) {
        Thread hookThread = new Thread() {
            public void run() {
                while (true) {
                    hookLabel.updateLocation();
                }
            } // end run
        }; // end thread creation
        hookThread.start();
    }

    private void setItemLabel(GamePage gamePage) {
        Thread itemThread = new Thread() {
            public void run() {
                ItemLabel item = new ItemLabel();

                add(item);

                add(item);
                while (true) {
                    if(hookLabel.getOriginalBound().intersects(item.getBounds())){
                        System.out.println("ItemLabel: " + item.getBounds().x + "," + item.getBounds().y);
                        break;
                    }

                    if (item.getBounds().intersects(hookLabel.getBounds())) {
                        //System.out.println(item.getBounds());
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
}

