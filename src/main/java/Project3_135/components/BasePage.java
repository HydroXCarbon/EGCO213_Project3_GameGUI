package Project3_135.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BasePage extends JPanel{

    protected final JPanel cardPanel;
    protected final CardLayout cardLayout;

    public BasePage(JPanel cardPanel, CardLayout cardLayout){
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }

    protected JButton createButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        setButtonStyle(button);
        return button;
    }

    protected void setButtonStyle(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(50, 0, 0, 0));
    }

    protected Component getComponentByName(String name) {
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof BasePage && component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }
}

