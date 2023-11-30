package Project3_135.model;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong

import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import java.awt.*;


public class TutorialMenu extends JPanel {

    private final GamePage pf;
    private final CardLayout cardLayout;
    private JLabel countdownLabel;
    private JLabel scoreLabel;
    private JLabel pauseLabel;
    private final JPanel cardPanel;
    private final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 20));
    private final JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));
    private JButton mainMenuButton;
    private JButton backButton;
    private JButton settingButton;
    private float opacity;
    private final float originalOpacity;
    private boolean visible = true;


    public TutorialMenu(float opacity, JPanel cardPanel, CardLayout cardLayout, GamePage pf) {
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
        setBorder(BorderFactory.createEmptyBorder(Utilities.FRAMEHEIGHT / 12, 0, 0, 0));
        setVisible(true);

        JLabel tutorialLabel = new JLabel("Press Spacebar to hook");
        tutorialLabel.setFont(new Font("Arial", Font.BOLD, 70));
        tutorialLabel.setForeground(Color.WHITE);

        JPanel textHolder = new JPanel();
        textHolder.setOpaque(false);
        textHolder.add(tutorialLabel, BorderLayout.CENTER);
        add(textHolder, BorderLayout.NORTH);
    }

    public boolean getVisibility() {
        return visible;
    }

    public void setVisibility(boolean visible) {
        setVisible(visible);
        this.visible = visible;
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
}
