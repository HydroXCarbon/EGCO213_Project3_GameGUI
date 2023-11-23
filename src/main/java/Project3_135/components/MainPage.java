package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;
import Project3_135.model.MySoundEffect;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPage extends BasePage {

    private final MySoundEffect startSound = new MySoundEffect(Utilities.START_SOUND_PATH);


    public MainPage(JPanel cardPanel, CardLayout cardLayout) {
        super(cardPanel, cardLayout);
        initializeComponents();
    }

    protected void initializeComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10000, 0));
        Border buttonBorder = new EmptyBorder(20, 0, 0, 0);
        int topMargin = 220;
        Border marginBorder = BorderFactory.createEmptyBorder(topMargin, 0, 0, 0);
        setBorder(marginBorder);

        // Load the default image icons
        ImageIcon playIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(80);
        ImageIcon settingIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(60);
        ImageIcon creditIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(60);

        // Load the hover image icons
        ImageIcon playIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(81);
        ImageIcon settingIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(61);
        ImageIcon creditIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(61);

        // Create buttons with default icons
        JButton playButton = createButton(playIcon);
        JButton settingButton = createButton(settingIcon);
        JButton creditButton = createButton(creditIcon);

        // Set the border for the buttons
        playButton.setBorder(buttonBorder);
        settingButton.setBorder(buttonBorder);
        creditButton.setBorder(buttonBorder);

        // Add components listener to the buttons
        playButton.addMouseListener(new MyMouseListener("gamePage", playIcon, playIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                startSound.setVolume(0.5f);
                startSound.playOnce();

                // Update selectBackground before creating the GamePage instance
                int selectBackground = ((SettingPage) getComponentByName("settingPage")).getSelectBackground();
                int selectIcon = ((SettingPage) getComponentByName("settingPage")).getSelectIcon();

                // Create a new instance of GamePage and add it to the cardPanel
                GamePage newGamePage = new GamePage(cardPanel, cardLayout, selectBackground, selectIcon);
                newGamePage.setName("gamePage");
                cardPanel.add(newGamePage, "gamePage");
                cardLayout.show(cardPanel, "gamePage");

                // Request focus for the new GamePage
                newGamePage.requestFocusInWindow();
            }
        });
        settingButton.addMouseListener(new MyMouseListener("settingPage", settingIcon, settingIconHover, cardPanel, cardLayout));
        creditButton.addMouseListener(new MyMouseListener("creditPage", creditIcon, creditIconHover, cardPanel, cardLayout));

        // Add components to the panel
        add(playButton, BorderLayout.SOUTH);
        add(settingButton, BorderLayout.SOUTH);
        add(creditButton, BorderLayout.SOUTH);
    }
}
