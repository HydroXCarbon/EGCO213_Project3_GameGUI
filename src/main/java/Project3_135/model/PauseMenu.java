package Project3_135.model;

import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PauseMenu extends JPanel {

    private float opacity;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private GamePage pf;
    private volatile boolean visible;

    public PauseMenu(float opacity, JPanel cardPanel, CardLayout cardLayout, GamePage pf) {
        this.pf = pf;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.opacity = opacity;
        setPreferredSize(new Dimension(Utilities.FRAMEWIDTH, Utilities.FRAMEHEIGHT));
        setOpaque(false);
        initializeComponents();
    }

    private void initializeComponents(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 10000, 0));
        setVisible(true);

        // Load the default image icons
        ImageIcon mainMenuIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(100);
        ImageIcon backIcon = new MyImageIcon(Utilities.PLAY_IMAGE_PATH).resize(80);

        // Load the hover image icons
        ImageIcon mainMenuIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(100);
        ImageIcon backIconHover = new MyImageIcon(Utilities.PLAY_HOVER_PATH).resize(80);

        // Create buttons with default icons
        JButton mainMenuButton = createButton(mainMenuIcon);
        JButton backButton = createButton(backIcon);

        mainMenuButton.addMouseListener(new MyMouseListener("mainPage", mainMenuIcon, mainMenuIconHover, cardPanel, cardLayout){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current progress will be lost.<br>Are you sure you want to proceed?</html>", "Confirm");

                if (confirmationDialog.isConfirmed()) {
                    pf.stopGame();
                    super.mouseClicked(e);
                }

                pf.requestFocusInWindow();
            }
        });
        backButton.addMouseListener(new MyMouseListener(null, backIcon, backIconHover, cardPanel, cardLayout){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pf.resumeGame();
                pf.requestFocusInWindow();
            }
        });

        add(mainMenuButton);
        add(backButton);
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
        SwingUtilities.invokeLater(() -> {
            setVisible(visible);
            revalidate();
            repaint();
        });
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
