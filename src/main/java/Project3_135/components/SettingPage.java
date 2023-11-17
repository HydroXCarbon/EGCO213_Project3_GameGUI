package Project3_135.components;

import Project3_135.model.MySoundEffect;

import javax.swing.*;
import java.awt.*;

public class SettingPage extends BasePage {
    private JSlider slider;
    private Integer selectBackground;

    public SettingPage(JPanel cardPanel, CardLayout cardLayout, MySoundEffect themeSound, Integer selectBackground) {
        super(cardPanel, cardLayout);
        this.selectBackground = selectBackground;
        initializeComponents(themeSound);
    }

    protected void initializeComponents(MySoundEffect themeSound) {
        setLayout(new BorderLayout());

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            String pageName = "mainPage";
            cardLayout.show(cardPanel, pageName);
        });

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        // Add components to the panel
        add(buttonPanel, BorderLayout.SOUTH);

        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setPaintTrack(true);
        slider.setInverted(true);
        slider.setLabelTable(slider.createStandardLabels(10));

        add(slider);
        setSize(300, 100);

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            float volume = (float) value / 100f;
            themeSound.setVolume(volume);
        });
        setVisible(true);
    }
}
