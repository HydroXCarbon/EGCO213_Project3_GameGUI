package Project3_135.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {

    private final String path;
    private final Icon defaultIcon;
    private final Icon hoverIcon;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public MyMouseListener(String path, Icon defaultIcon, Icon hoverIcon, JPanel cardPanel, CardLayout cardLayout){
        this.path = path;
        this.defaultIcon = defaultIcon;
        this.hoverIcon = hoverIcon;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        cardLayout.show(cardPanel, path);

        // Find the index of the component and set focus
        Component[] components = cardPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getName() != null && components[i].getName().equals(path)) {
                components[i].requestFocusInWindow();
                break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getComponent() instanceof JButton) {
            ((JButton) e.getComponent()).setIcon(hoverIcon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent() instanceof JButton) {
            ((JButton) e.getComponent()).setIcon(defaultIcon);
        }
    }
}