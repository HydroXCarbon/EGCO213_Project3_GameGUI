package Project3_135.components;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong
import Project3_135.Utilities;
import Project3_135.model.MyImageIcon;
import Project3_135.model.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class CreditPage extends BasePage {
    JLabel name1, name2, name3;
    private final String backgroundPath = Utilities.CREDIT_BACKGROUND_PATH;

    public CreditPage(JPanel cardPanel, CardLayout cardLayout) {
        super(cardPanel, cardLayout);
        initializeComponents();
    }

    protected void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JPanel componentHolder = new JPanel();
        componentHolder.setLayout(new BoxLayout(componentHolder, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Credits");
        title.setFont(new Font("Arial", Font.BOLD, 150));
        title.setForeground(Color.WHITE);

        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);
        add(componentHolder, BorderLayout.CENTER);

        createNameList();
        createBackButton();

    }

    private void createNameList() {
        name1 = new JLabel("Purin     Pongpanich  6513135");
        name1.setFont(new Font("Arial", Font.PLAIN, 60));
        name1.setForeground(Color.WHITE);
        name1.setOpaque(false);

        name2 = new JLabel("Jarupat Chodsitanan 6513163");
        name2.setFont(new Font("Arial", Font.PLAIN, 60));
        name2.setForeground(Color.WHITE);
        name2.setOpaque(false);

        name3 = new JLabel("Chalisa Buathong      6513165");
        name3.setFont(new Font("Arial", Font.PLAIN, 60));
        name3.setForeground(Color.WHITE);
        name3.setOpaque(false);

        JPanel nameListHolder = new JPanel();
        //nameListHolder.setBackground(Color.gray);
        nameListHolder.setOpaque(false);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());

        verticalBox.add(name1);
        verticalBox.add(name2);
        verticalBox.add(name3);

        verticalBox.add(Box.createVerticalGlue());

        nameListHolder.add(verticalBox);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(nameListHolder);
        centerPanel.setOpaque(false);

        add(centerPanel, BorderLayout.CENTER);
    }


    private void createBackButton() {
        ImageIcon backIcon = new MyImageIcon(Utilities.BACK_BUTTON_IMAGE_PATH).resize(60);
        ImageIcon backIconHover = new MyImageIcon(Utilities.BACK_HOVER_BUTTON_PATH).resize(60);

        JButton backbutton = createButton(backIcon);
        backbutton.addMouseListener(new MyMouseListener("mainPage", backIcon, backIconHover, cardPanel, cardLayout));

        JPanel backbuttonHolder = new JPanel();
        backbuttonHolder.setBackground(Color.RED);
        backbuttonHolder.setOpaque(false);
        backbuttonHolder.add(backbutton);

        add(backbuttonHolder, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image backgroundImage = new MyImageIcon(backgroundPath).getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}