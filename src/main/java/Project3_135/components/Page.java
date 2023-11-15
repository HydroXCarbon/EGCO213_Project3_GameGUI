package Project3_135.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page extends BasePage{
    public Page(JPanel cardPanel, CardLayout cardLayout, Color color){
        super(cardPanel, cardLayout);

        initializeComponents(color);
    }

    @Override
    protected void initializeComponents(Color color) {
        setLayout(new BorderLayout());
        setBackground(color);

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pageName = "mainPage";
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
