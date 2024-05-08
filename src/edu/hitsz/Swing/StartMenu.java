package edu.hitsz.Swing;

import edu.hitsz.Game.EasyGame;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel MainPanel;
    private JLabel AircraftWar;
    private JButton start;
    private JButton RankingButton;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public StartMenu() {
        start.addActionListener(e -> {
            Main.cardPanel.add(new Setting().getMainPanel(), "setting");
            Main.cardLayout.show(Main.cardPanel, "setting");
        });

        RankingButton.addActionListener(e -> {
            Main.cardPanel.add(new BoardChoosing().getMainPanel(), "BoardChoosing");
            Main.cardLayout.show(Main.cardPanel, "BoardChoosing");
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
