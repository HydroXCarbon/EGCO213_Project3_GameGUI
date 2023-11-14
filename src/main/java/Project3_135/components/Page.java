package Project3_135.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page extends JPanel{
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public Page(JPanel cardPanel, CardLayout cardLayout, Color color){
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;

        initializeComponents(color);
    }

    private void initializeComponents(Color color) {
        setLayout(new BorderLayout());
        setBackground(color);

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pageName = "main";
                cardLayout.show(cardPanel, pageName);
            }
        });

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        // Add components to the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
