package Project3_135.components;

import Project3_135.model.MySoundEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPage extends BasePage {
    private JSlider slider;
    private int selectBackground = 5;

    public SettingPage(JPanel cardPanel, CardLayout cardLayout, MySoundEffect themeSound) {
        super(cardPanel, cardLayout);
        initializeComponents(themeSound);
    }

    protected void initializeComponents(MySoundEffect themeSound) {
        setLayout(new BorderLayout());
        // Create a panel to hold components
        JPanel componentHolder = new JPanel();
        componentHolder.setPreferredSize(new Dimension(400, 300));

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            String pageName = "mainPage";
            cardLayout.show(cardPanel, pageName);
        });

        // Create Radio button
        JRadioButton radioButton1 = new JRadioButton("Mine");
        JRadioButton radioButton2 = new JRadioButton("Forest");
        JRadioButton radioButton3 = new JRadioButton("Desert");
        JRadioButton radioButton4 = new JRadioButton("Lava");
        JRadioButton radioButton5 = new JRadioButton("Random");

        radioButton1.addActionListener(new RadioButtonListener(1));
        radioButton2.addActionListener(new RadioButtonListener(2));
        radioButton3.addActionListener(new RadioButtonListener(3));
        radioButton4.addActionListener(new RadioButtonListener(4));
        radioButton5.addActionListener(new RadioButtonListener(5));

        // Create a ButtonGroup to group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        buttonGroup.add(radioButton5);

        radioButton5.setSelected(true);

        componentHolder.add(radioButton1);
        componentHolder.add(radioButton2);
        componentHolder.add(radioButton3);
        componentHolder.add(radioButton4);
        componentHolder.add(radioButton5);

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
        slider.setLabelTable(slider.createStandardLabels(10));

        componentHolder.add(slider);
        add(componentHolder, BorderLayout.CENTER);

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            float volume = (float) value / 100f;
            themeSound.setVolume(volume);
        });
        setVisible(true);
    }

    // ActionListener for radio buttons
    private class RadioButtonListener implements ActionListener {

        private int buttonData;

        public RadioButtonListener(int buttonData) {
            this.buttonData = buttonData;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectBackground = buttonData;
        }
    }

    public Integer getSelectBackground() {
        return selectBackground;
    }
}
