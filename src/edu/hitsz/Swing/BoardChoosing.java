package edu.hitsz.Swing;

import edu.hitsz.Game.EasyGame;
import edu.hitsz.Game.Game;
import edu.hitsz.Game.HardGame;
import edu.hitsz.Game.MediumGame;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

public class BoardChoosing {
    private JPanel MainPanel;
    private JPanel difficulty;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton returnButton;
    private int level;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private void actionButton(JButton button) {
        button.addActionListener(e -> {
            switch (e.getActionCommand()) {
                case "简单模式" -> level = 1;
                case "普通模式" -> level = 2;
                case "困难模式" -> level = 3;
            }
            Main.cardPanel.add(new BoardChoosing().getMainPanel(), "BoardChoosing");
            Main.cardPanel.add(new RankingBoard(level).getMainPanel(), "RankingBoard");
            Main.cardLayout.show(Main.cardPanel, "RankingBoard");
        });
    }

    public BoardChoosing() {
        actionButton(easyButton);
        actionButton(mediumButton);
        actionButton(hardButton);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.cardPanel, "start");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ModeChoosing");
        frame.setContentPane(new BoardChoosing().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
