package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        String line1 = "Game Over!";
        String line2 = "Your Score: " + score;

        // Add a component listener to the text area
        textArea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Calculate the number of spaces needed to center each line of text
                String centeredLine1 = centerLine(line1, textArea, customFont);
                String centeredLine2 = centerLine(line2, textArea, customFont);

                // Set the centered text
                textArea.setText(centeredLine1 + "\n" + centeredLine2);
            }
        });



        // Load the default image icons
        ImageIcon mainMenuIcon = new MyImageIcon(Utilities.MENU_BUTTON_IMAGE_PATH).resize(70);

        // Load the hover image icons
        ImageIcon mainMenuIconHover = new MyImageIcon(Utilities.MENU_HOVER_BUTTON_PATH).resize(70);

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
        textAreaHolder.setOpaque(false);
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

    private String centerLine(String line, JTextArea textArea, Font font) {
        int textAreaWidth = textArea.getWidth();
        int textWidth = textArea.getFontMetrics(font).stringWidth(line);
        int spacesNeeded = (textAreaWidth - textWidth) / textArea.getFontMetrics(font).charWidth(' ');

        // Add the spaces to the beginning of the text string
        StringBuilder sb = new StringBuilder(line);
        for (int i = 0; i < spacesNeeded/2; i++) {
            sb.insert(0, " ");
        }
        return sb.toString();
    }
}
