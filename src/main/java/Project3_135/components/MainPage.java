package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.MyDocumentListener;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class MainPage extends BasePage {

    private Integer selectBackground = 5;

    public MainPage(JPanel cardPanel, CardLayout cardLayout) {
        super(cardPanel, cardLayout);
        initializeComponents();
    }

    protected void initializeComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10000, 0));

        // Create a text field
        JTextField textField = new JTextField(15);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Load the default image icons
        ImageIcon playIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(100);
        ImageIcon settingIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(80);
        ImageIcon creditIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(80);

        // Load the hover image icons
        ImageIcon playIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(100);
        ImageIcon settingIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(80);
        ImageIcon creditIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(80);

        // Create buttons with default icons
        JButton playButton = createButton(playIcon);
        JButton settingButton = createButton(settingIcon);
        JButton creditButton = createButton(creditIcon);

        // Add components listener to the buttons
        playButton.addMouseListener(new MyMouseListener("gamePage", playIcon, playIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                // Create a new instance of GamePage and add it to the cardPanel
                GamePage newGamePage = new GamePage(cardPanel, cardLayout, selectBackground);
                newGamePage.setName("gamePage");
                cardPanel.add(newGamePage, "gamePage");
                cardLayout.show(cardPanel, "gamePage");

                // Request focus for the new GamePage
                newGamePage.requestFocusInWindow();
            }
        });
        settingButton.addMouseListener(new MyMouseListener("settingPage", settingIcon, settingIconHover, cardPanel, cardLayout));
        creditButton.addMouseListener(new MyMouseListener("creditPage", creditIcon, creditIconHover, cardPanel, cardLayout));
        textField.getDocument().addDocumentListener(new MyDocumentListener());

        // Add components to the panel
        add(playButton);
        add(settingButton);
        add(creditButton);
        add(textField);
    }
}
