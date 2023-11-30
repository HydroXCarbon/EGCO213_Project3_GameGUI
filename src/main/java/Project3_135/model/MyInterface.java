package Project3_135.model;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public interface MyInterface {

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
