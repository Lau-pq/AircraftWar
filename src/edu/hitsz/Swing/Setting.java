package edu.hitsz.Swing;

import edu.hitsz.Game.EasyGame;
import edu.hitsz.Game.HardGame;
import edu.hitsz.Game.MediumGame;
import edu.hitsz.application.Main;

import javax.swing.*;
public class Setting {
    private JPanel MainPanel;
    private JPanel difficulty;
    private JPanel sound;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JComboBox musicComboBox;
    private JLabel soundLabel;
    private JButton returnButton;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private void actionButton(JButton button) {
        button.addActionListener(e -> {
            switch (e.getActionCommand()) {
                case "简单模式" -> Main.game = new EasyGame();
                case "普通模式" -> Main.game = new MediumGame();
                case "困难模式" -> Main.game = new HardGame();
            }
            Main.cardPanel.add(Main.game, "game");
            Main.cardLayout.show(Main.cardPanel, "game");
            Main.game.action();
        });
    }

    public Setting() {
        actionButton(easyButton);
        actionButton(mediumButton);
        actionButton(hardButton);

        returnButton.addActionListener(e -> Main.cardLayout.show(Main.cardPanel, "start"));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Setting");
        frame.setContentPane(new Setting().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
