package Project3_135;

import Project3_135.components.GamePage;
import Project3_135.components.MainPage;
import Project3_135.components.Page;

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
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel and layout manager for the card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create different pages (JPanel) for your application
        JPanel mainPage = new MainPage(cardPanel, cardLayout, Color.RED);
        mainPage.setName("mainPage");

        JPanel gamePage = new GamePage(cardPanel, cardLayout, Color.GREEN);
        gamePage.setName("gamePage");

        JPanel settingPage = new Page(cardPanel, cardLayout, Color.BLUE);
        settingPage.setName("settingPage");

        JPanel creditPage = new Page(cardPanel, cardLayout, Color.BLUE);
        creditPage.setName("creditPage");

        // Add pages to cardPanel
        cardPanel.add(mainPage, "mainPage");
        cardPanel.add(gamePage, "gamePage");
        cardPanel.add(settingPage, "settingPage");
        cardPanel.add(creditPage, "creditPage");

        // Add cardPanel to the frame
        setContentPane(cardPanel);
        setVisible(true);
    }
}
