package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.HookLabel;
import Project3_135.model.ItemLabel;
import Project3_135.model.MyImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePage extends BasePage {

    private final int itemAmount = Utilities.ITEMAMOUNT;
    private final int[] line = new int[4];
    private final List<ItemLabel> itemList = new CopyOnWriteArrayList<>();
    private int timer;
    private int totalScore = 0;
    private int itemCount = 0;
    private HookLabel hookLabel;
    private Image backgroundImage;
    private boolean pause = false;
    private boolean gameEnd = false;


    public GamePage(JPanel cardPanel, CardLayout cardLayout, int selectBackground) {
        super(cardPanel, cardLayout);
        initializeComponents(selectBackground);
    }

    protected void initializeComponents(int selectBackground) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        createBackground(selectBackground);

        createNewButton();

        createHookObject();

        startTimer();

        // Set Thread for item
        for (int i = 0; i < itemAmount; i++) {
            setItemLabel(this);
        }

        // Set SwingWorker for hook
        SwingWorker<Void, Void> hookWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    if (pause) {
                        Thread.sleep(10);
                        continue;
                    }
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
                gameEnd();
            }
        };
        hookWorker.execute();
    }

    private void createBackground(int selectBackground){
        // Create background from selectBackground ( 5 = random )
        String backgroundPath = "";
        Random random = new Random();

        if (selectBackground == 5) {
            selectBackground = random.nextInt(4) + 1;
        }

        switch (selectBackground) {
            case 1:
                backgroundPath = Utilities.BACKGROUND1_IMAGE_PATH;
                break;
            case 2:
                backgroundPath = Utilities.BACKGROUND2_IMAGE_PATH;
                break;
            case 3:
                backgroundPath = Utilities.BACKGROUND3_IMAGE_PATH;
                break;
            case 4:
                backgroundPath = Utilities.BACKGROUND4_IMAGE_PATH;
                break;
        }
        backgroundImage = new MyImageIcon(backgroundPath).getImage();
    }

    private void createHookObject() {
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
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameEnd) {
                    pause = !pause;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void createNewButton() {
        // Create a panel for the top-left corner
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false);
        add(topLeftPanel, BorderLayout.NORTH);

        // Load the default image icons
        ImageIcon pauseIcon = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_IMAGE_PATH).resize(70);

        // Load the hover image icons
        ImageIcon pauseIconHover = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_HOVER_IMAGE_PATH).resize(70);

        // Create the settingButton with default icons
        JButton settingButton = createButton(pauseIcon);
        settingButton.setOpaque(false);

        // Add components listener to the buttons
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(gameEnd){
                    return;
                }

                pause = !pause;
                if (!pause) {
                    GamePage.this.requestFocusInWindow();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getComponent() instanceof JButton) {
                    ((JButton) e.getComponent()).setIcon(pauseIconHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getComponent() instanceof JButton) {
                    ((JButton) e.getComponent()).setIcon(pauseIcon);
                }
            }
        });

        // Add the button to the top-left panel with FlowLayout
        topLeftPanel.add(settingButton);
    }

    private void startTimer() {
        final int[] countdownSeconds = {Utilities.GAMETIME + 1};

        Timer timer = new Timer(1000, e -> {
            if (pause) {
                return;
            }
            if (countdownSeconds[0] > 0) {
                countdownSeconds[0]--;
                this.timer = countdownSeconds[0]; // Update the class variable
                System.out.println("Time left: " + countdownSeconds[0] + " seconds");
            } else {
                ((Timer) e.getSource()).stop();
                gameEnd();
            }
        });

        timer.start();
    }

    private void setItemLabel(GamePage gamePage) {
        Thread itemThread = new Thread() {
            public void run() {
                boolean firstHit = false;
                final ItemLabel[] item = {null};

                // Create a new item
                createNewItem(item);
                add(item[0]);

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

    private void gameEnd(){
        pause = true;
        gameEnd = true;
        System.out.println("Game End");
    }

}

