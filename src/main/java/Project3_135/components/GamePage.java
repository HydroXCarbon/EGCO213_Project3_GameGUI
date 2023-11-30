package Project3_135.components;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong
import Project3_135.Utilities;
import Project3_135.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;


public class GamePage extends BasePage {

    private PauseMenu pauseMenu;
    private TutorialMenu tutorialMenu;
    private HookLabel hookLabel;
    private Image backgroundImage;
    private int totalScore = 0;
    private int itemCount = 0;
    private final int[] line = new int[4];
    private final int itemAmount = Utilities.ITEMAMOUNT;
    private final List<ItemLabel> itemList = new CopyOnWriteArrayList<>();
    private final CountDownLatch itemLatch = new CountDownLatch(itemAmount);
    private final MySoundEffect finishSound = new MySoundEffect(Utilities.FINISH_SOUND_PATH);
    private boolean pause = false;
    private boolean gameEnd = false;


    public GamePage(JPanel cardPanel, CardLayout cardLayout, int selectBackground, int selectIcon) {
        super(cardPanel, cardLayout);
        initializeComponents(selectBackground, selectIcon);
    }

    private void initializeComponents(int selectBackground, int selectIcon) {
        setLayout(new BorderLayout());

        createBackground(selectBackground);
        createHookObject(selectIcon);

        // Set Thread for item
        for (int i = 0; i < itemAmount; i++) {
            setItemLabel(this);
        }

        try {
            itemLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createTutorialMenu();

        // Add key listener
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if(tutorialMenu.getVisibility()){
                        tutorialMenu.setVisibility(false);
                        startTimer();
                        createPauseMenu();
                        hookLabel.setMove();
                    }else{
                        hookLabel.setMove();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameEnd) {
                    pauseGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

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

    private void createTutorialMenu(){
        tutorialMenu = new TutorialMenu(0.7f, cardPanel, cardLayout, this);
        tutorialMenu.setBackground(new Color(31, 31, 31, 255));
        tutorialMenu.setVisibility(true);
        add(tutorialMenu, BorderLayout.CENTER);
    }

    private void createPauseMenu() {
        pauseMenu = new PauseMenu(0.7f, cardPanel, cardLayout, this);
        pauseMenu.setBackground(new Color(31, 31, 31, 255));
        pauseMenu.setVisibility(false);
        add(pauseMenu, BorderLayout.CENTER);
        setComponentZOrder(pauseMenu, 0);
    }


    private void createBackground(int selectBackground) {
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

    private void createHookObject(int selectIcon) {
        // Create object

        hookLabel = new HookLabel(this, line, selectIcon);

        // Set initial location for hook
        hookLabel.setInitial(Utilities.FRAMEWIDTH / 2, 150);

        // Add components
        add(hookLabel);
    }

    private void startTimer() {
        final int[] countdownSeconds = {Utilities.GAMETIME};

        Timer timer = new Timer(1000, e -> {
            if (pause) {
                return;
            }
            if (countdownSeconds[0] > 0) {
                countdownSeconds[0]--;
                pauseMenu.setCountdown(countdownSeconds[0]+ 1);
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

                itemLatch.countDown();

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
        int maxAttempts = 100;
        int attempts = 0;

        do {
            item[0] = new ItemLabel();
            attempts++;
            if (attempts > maxAttempts) {
                return;
            }
        } while (isOverlapping(item[0]));

        itemList.add(item[0]);
    }

    private boolean isOverlapping(ItemLabel newItem) {
        synchronized (itemList) {
            for (ItemLabel existingItem : itemList) {
                if (existingItem.getBounds().intersects(newItem.getBounds())) {
                    return true;
                }
                if(hookLabel.getOriginalBound().intersects(newItem.getBounds())){
                    return true;
                }
            }
            return false;
        }
    }

    synchronized private void updateScore(int score) {
        totalScore += score;
        pauseMenu.setScore(totalScore);
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

        // Restore the original stroke
        g2d.setStroke(oldStroke);
    }

    private void gameEnd() {
        pause = true;
        gameEnd = true;
        finishSound.setVolume(0.5f);
        finishSound.playOnce();
        SwingUtilities.invokeLater(() -> {
            GameEndPage newGameEndPage = new GameEndPage(cardPanel, cardLayout, totalScore);
            newGameEndPage.setName("gameEndPage");
            cardPanel.add(newGameEndPage, "gameEndPage");
            cardLayout.show(cardPanel, "gameEndPage");
        });
        cardPanel.remove(this);
    }

    public boolean checkGameEnd() {
        return gameEnd;
    }

    public void stopGame() {
        pause = true;
        gameEnd = true;
        cardPanel.remove(this);
    }

    public void pauseGame() {
        pause = !pause;
        SwingUtilities.invokeLater(() -> {
            pauseMenu.setVisibility(pause);
            repaint();
        });
    }

    public void resumeGame() {
        pause = false;
        SwingUtilities.invokeLater(() -> {
            pauseMenu.setVisibility(pause);
            GamePage.this.requestFocusInWindow();
            repaint();
        });
    }

}

