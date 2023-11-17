package Project3_135.model;

import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PauseMenu extends JPanel {

    private JButton mainMenuButton;
    private JButton backButton;
    private JButton settingButton;
    private float opacity;
    private float originalOpacity;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private GamePage pf;
    private JPanel centerPanel;
    private volatile boolean visible;

    public PauseMenu(float opacity, JPanel cardPanel, CardLayout cardLayout, GamePage pf) {
        this.pf = pf;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.originalOpacity = opacity;
        this.opacity = opacity;
        setPreferredSize(new Dimension(Utilities.FRAMEWIDTH, Utilities.FRAMEHEIGHT));
        setOpaque(false);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setVisible(true);

        // Load the default image icons
        ImageIcon mainMenuIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(100);
        ImageIcon backIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(100);
        ImageIcon pauseIcon = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_IMAGE_PATH).resize(70);

        // Load the hover image icons
        ImageIcon mainMenuIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(100);
        ImageIcon backIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(100);
        ImageIcon pauseIconHover = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_HOVER_IMAGE_PATH).resize(70);

        // Create buttons with default icons
        mainMenuButton = createButton(mainMenuIcon);
        backButton = createButton(backIcon);
        settingButton = createButton(pauseIcon);

        // Add components listener to the buttons
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (pf.checkGameEnd()) {
                    return;
                }
                pf.pauseGame();
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

        mainMenuButton.addMouseListener(new MyMouseListener("mainPage", mainMenuIcon, mainMenuIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current progress will be lost.<br>Are you sure you want to proceed?</html>", "Confirm");

                if (confirmationDialog.isConfirmed()) {
                    pf.stopGame();
                    super.mouseClicked(e);
                }
            }
        });

        backButton.addMouseListener(new MyMouseListener(null, backIcon, backIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pf.resumeGame();
            }
        });

        // Create a panel for the top-left corner
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        topLeftPanel.setOpaque(false);
        topLeftPanel.add(settingButton);

        // Create a panel for the center region with a vertical box layout
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));
        centerPanel.setOpaque(false);


        // Add "Pause" text to the center panel
        JLabel pauseLabel = new JLabel("Pause", SwingConstants.CENTER);
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 200));
        centerPanel.add(pauseLabel, BorderLayout.NORTH);

        // Add buttons to the center panel
        centerPanel.add(mainMenuButton, BorderLayout.SOUTH);
        centerPanel.add(backButton, BorderLayout.SOUTH);

        // Add the top-left panel to the main panel
        add(topLeftPanel, BorderLayout.NORTH);
        // Add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);
    }


    public void setVisibility(boolean visible) {
        this.visible = visible;
        centerPanel.setVisible(visible);
        if(visible){
            opacity = originalOpacity;
        }else{
            opacity = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Use AlphaComposite to set the opacity
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        // Draw the panel content
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    private JButton createButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        setButtonStyle(button);
        return button;
    }

    private void setButtonStyle(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(50, 0, 0, 0));
    }
}
