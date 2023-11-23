package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameEndPage extends BasePage{

    private int score;
    private final String backgroundPath = Utilities.GAMEEND_BACKGROUND_PATH;

    public GameEndPage(JPanel cardPanel, CardLayout cardLayout, int score) {
        super(cardPanel, cardLayout);
        this.score = score;
        initializeComponents();
    }

    private void initializeComponents(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(200, 0, 50, 0));

        // Create text area
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(500, 200));

        // Set font and size
        Font customFont = new Font("Arial", Font.PLAIN, 60);
        textArea.setFont(customFont);

        // Set text
        score = 1000;
        String endGameMessage = "      Game Over!    Your Score: " + score;
        textArea.setText(endGameMessage);

        // Load the default image icons
        ImageIcon mainMenuIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(70);

        // Load the hover image icons
        ImageIcon mainMenuIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(70);

        // Create the button
        JButton mainMenuButton = createButton(mainMenuIcon);

        // Add components listener to the buttons
        mainMenuButton.addMouseListener(new MyMouseListener("gamePage", mainMenuIcon, mainMenuIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(cardPanel, "mainMenu");
                cardPanel.remove(GameEndPage.this);
            }
        });

        JPanel textAreaHolder = new JPanel();
        textAreaHolder.setOpaque(true);
        textAreaHolder.add(textArea, BorderLayout.WEST);
        textAreaHolder.setPreferredSize(new Dimension(getWidth(), 200));

        // Add components to the page
        add(textAreaHolder, BorderLayout.CENTER);
        add(mainMenuButton, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image backgroundImage = new MyImageIcon(backgroundPath).getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
