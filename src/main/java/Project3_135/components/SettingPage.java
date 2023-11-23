package Project3_135.components;

import Project3_135.Utilities;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;
import Project3_135.model.MySoundEffect;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPage extends BasePage {

    private JSlider slider;
    private int selectBackground = 5;
    private int selectIcon = 5;
    private final JLabel hookPreview = new JLabel();
    private final JPanel componentHolder = new JPanel();

    public SettingPage(JPanel cardPanel, CardLayout cardLayout, MySoundEffect themeSound) {
        super(cardPanel, cardLayout);
        initializeComponents(themeSound);
    }

    protected void initializeComponents(MySoundEffect themeSound) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Create a panel to hold components
        componentHolder.setLayout(new BoxLayout(componentHolder, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Setting");
        title.setFont(new Font("Arial", Font.BOLD, 150));
        title.setForeground(Color.WHITE);

        titlePanel.add(title, new GridBagConstraints());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titlePanel, BorderLayout.NORTH);

        createComboBox();
        createRadiobutton();
        createSlider(themeSound);

        add(componentHolder, BorderLayout.CENTER);

        createBackButton();
    }

    private void createComboBox() {
        // Create a combo box
        String[] iconList = {"Grabber", "Toy Grabber", "Scientist Grabber", "Magnet Hook", "Random"};
        JComboBox<String> iconComboBox = new JComboBox<>(iconList);
        iconComboBox.setSelectedIndex(4);
        iconComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                selectIcon = cb.getSelectedIndex() + 1;
                updateHookPreview();
            }
        });

        JPanel comboBoxHolder = new JPanel();
        comboBoxHolder.setOpaque(false);
        comboBoxHolder.add(iconComboBox);

        JLabel hookPreview = createPreview();

        JPanel Holder = new JPanel();
        Holder.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        Holder.setOpaque(false);
        Holder.add(comboBoxHolder, BorderLayout.WEST);
        Holder.add(hookPreview, BorderLayout.EAST);

        componentHolder.add(Holder);
    }

    private JLabel createPreview() {
        String hookPath = getHookImagePath();
        hookPreview.setPreferredSize(new Dimension(150, 150));

        hookPreview.setIcon(new MyImageIcon(hookPath).resize(100));
        hookPreview.setOpaque(false);
        return hookPreview;
    }

    private void updateHookPreview() {
        String hookPath = getHookImagePath();
        hookPreview.setIcon(new MyImageIcon(hookPath).resize(100));
    }

    private String getHookImagePath(){
        String hookPath = "";
        switch (selectIcon) {
            case 1:
                hookPath = Utilities.HOOK1_IMAGE_PATH;
                break;
            case 2:
                hookPath = Utilities.HOOK2_IMAGE_PATH;
                break;
            case 3:
                hookPath = Utilities.HOOK3_IMAGE_PATH;
                break;
            case 4:
                hookPath = Utilities.HOOK4_IMAGE_PATH;
                break;
            case 5:
                hookPath = Utilities.QUESTIONMARK_IMAGE_PATH;
                break;
        }
        return hookPath;
    }

    private void createBackButton() {
        ImageIcon backIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(60);
        ImageIcon backIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(60);
        JButton backButton = createButton(backIcon);
        backButton.addMouseListener(new MyMouseListener("mainPage", backIcon, backIconHover, cardPanel, cardLayout));

        JPanel backButtonHolder = new JPanel();
        backButtonHolder.setOpaque(false);
        backButtonHolder.add(backButton);

        add(backButtonHolder, BorderLayout.SOUTH);
    }

    private void createRadiobutton() {
        // Create Radio button
        JRadioButton radioButton1 = new JRadioButton("Mine");
        JRadioButton radioButton2 = new JRadioButton("Meadow");
        JRadioButton radioButton3 = new JRadioButton("Stony");
        JRadioButton radioButton4 = new JRadioButton("Magma");
        JRadioButton radioButton5 = new JRadioButton("Random");

        radioButton1.addActionListener(new RadioButtonListener(1));
        radioButton2.addActionListener(new RadioButtonListener(2));
        radioButton3.addActionListener(new RadioButtonListener(3));
        radioButton4.addActionListener(new RadioButtonListener(4));
        radioButton5.addActionListener(new RadioButtonListener(5));

        radioButton1.setOpaque(false);
        radioButton2.setOpaque(false);
        radioButton3.setOpaque(false);
        radioButton4.setOpaque(false);
        radioButton5.setOpaque(false);

        // Create a ButtonGroup to group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        buttonGroup.add(radioButton5);

        radioButton5.setSelected(true);

        JPanel radioButtonHolder = new JPanel();
        radioButtonHolder.setOpaque(false);

        radioButtonHolder.add(radioButton1);
        radioButtonHolder.add(radioButton2);
        radioButtonHolder.add(radioButton3);
        radioButtonHolder.add(radioButton4);
        radioButtonHolder.add(radioButton5);

        componentHolder.add(radioButtonHolder);

    }

    private void createSlider(MySoundEffect themeSound) {
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setSnapToTicks(true);
        slider.setPaintTrack(true);
        slider.setFont(new Font("MV Boli", Font.PLAIN, 15));
        slider.setLabelTable(slider.createStandardLabels(10));

        // Set the custom thumb icon (gray circle)
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintFocus(Graphics g) {
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Rectangle knobBounds = thumbRect;

                // Calculate the center of the bounding rectangle
                int centerX = knobBounds.x + knobBounds.width / 2;
                int centerY = knobBounds.y + knobBounds.height / 2;

                // Determine the radius as half of the minimum dimension
                int radius = Math.min(knobBounds.width, knobBounds.height) / 2;

                // Draw a gray circle
                g2d.setColor(new Color(96, 96, 96));
                g2d.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Rectangle trackBounds = trackRect;

                // Set the color
                g2d.setColor(Color.GRAY);

                // Set the line width
                float lineWidth = 2.0f; // Set your desired line width here
                g2d.setStroke(new BasicStroke(lineWidth));

                // Calculate the y-coordinate for the line
                int lineY = trackBounds.y + trackBounds.height / 2;

                // Draw the track
                g2d.drawLine(trackBounds.x, lineY, trackBounds.x + trackBounds.width, lineY);
            }
        });

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            float volume = (float) value / 100f;
            themeSound.setVolume(volume);
        });
        setVisible(true);

        slider.setPreferredSize(new Dimension(600, slider.getPreferredSize().height * 2));

        JPanel sliderHolder = new JPanel();
        sliderHolder.setOpaque(false);
        sliderHolder.setPreferredSize(new Dimension(getWidth() * 7 / 10, getHeight()));
        sliderHolder.add(slider);

        componentHolder.add(sliderHolder);
    }

    public int getSelectBackground() {
        return selectBackground;
    }

    public int getSelectIcon() {
        return selectIcon;
    }

    // ActionListener for radio buttons
    private class RadioButtonListener implements ActionListener {

        private final int buttonData;

        public RadioButtonListener(int buttonData) {
            this.buttonData = buttonData;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectBackground = buttonData;
        }
    }
}
