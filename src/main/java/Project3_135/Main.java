package Project3_135;

import javax.swing.*;
import java.awt.*;


class Main extends JFrame {
    // components
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final int framewidth = Utilities.FRAMEWIDTH;
    private final int frameheight = Utilities.FRAMEHEIGHT;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().MainApplication());
    }

    public void MainApplication() {
        // Set up the frame
        setTitle("Mining Gold");
        setSize(framewidth, frameheight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel and layout manager for the card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create page constructor object
        Router router = new Router(cardPanel, cardLayout);

        // Create different pages (JPanel) for your application
        JPanel mainPage = router.createMainPage(Color.RED);
        JPanel gamePage = router.createPage(Color.GREEN);
        JPanel settingPage = router.createPage(Color.BLUE);
        JPanel creditPage = router.createPage(Color.black);

        // Add pages to cardPanel
        cardPanel.add(mainPage, "main");
        cardPanel.add(gamePage, "game");
        cardPanel.add(settingPage, "setting");
        cardPanel.add(creditPage, "credit");

        // Add cardPanel to the frame
        setContentPane(cardPanel);
        setVisible(true);
    }
}
