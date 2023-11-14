package Project3_135;

import Project3_135.components.MainPage;
import Project3_135.components.Page;

import javax.swing.*;
import java.awt.*;

public class Router extends JFrame {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Router(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }

    public JPanel createMainPage(Color color) {
        return new MainPage(cardPanel, cardLayout, color);
    }

    public JPanel createPage(Color color) {
        return new Page(cardPanel, cardLayout, color);
    }
}
