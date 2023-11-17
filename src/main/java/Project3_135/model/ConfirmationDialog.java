package Project3_135.model;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationDialog extends JDialog {

    private boolean confirmed = false;

    public ConfirmationDialog(String message, String title) {
        // Set dialog properties
        setTitle(title);
        setModal(true);
        setSize(300, 150);
        setLocationRelativeTo(null);

        // Create components
        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(JLabel.CENTER);
        JButton yesButton = new JButton("Quit");
        JButton noButton = new JButton("Back");

        // Add action listeners to buttons
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose(); // Close the dialog
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        // Set layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the dialog
        add(panel);

        // Show the dialog
        setVisible(true);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

