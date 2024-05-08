package edu.hitsz.Swing;

import edu.hitsz.DAO.RecordDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.hitsz.DAO.Record;
import edu.hitsz.DAO.RecordDaoImpl;
import edu.hitsz.Game.Game;
import edu.hitsz.application.Main;

public class RankingBoard {
    private JPanel MainPanel;
    private JLabel RankingBoardLabel;
    private JButton deleteButton;
    private JTable scoreTable;
    private JScrollPane tableScrollPanel;
    private JButton returnButton;
    private JLabel difficultyLabel;

    private DefaultTableModel model;
    private List<Record> records;
    private final RecordDao recordDao;
    private String userName;

    public RankingBoard(int level) {
//        if (Game.level == 1)
//            difficultyLabel.setText("难度：简单");
//        else if (Game.level == 2)
//            difficultyLabel.setText("难度：普通");
//        else if (Game.level == 3)
//            difficultyLabel.setText("难度：困难");
//        else
//            difficultyLabel.setText("难度：未知");

        recordDao = new RecordDaoImpl(level);
        records = recordDao.getAllRecords();
        model = recordDao.toSwingTable(records);
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(e -> {
            int[] rows = scoreTable.getSelectedRows();
            System.out.println(Arrays.toString(rows));
            int option = JOptionPane.showConfirmDialog(null, "是否确定删除", "提示",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(JOptionPane.YES_OPTION == option && rows != null && rows.length != 0) {
                recordDao.deleteRecords(rows);
                records = recordDao.getAllRecords();
                model = recordDao.toSwingTable(records);
                scoreTable.setModel(model);
                tableScrollPanel.setViewportView(scoreTable);
            }
        });

        returnButton.addActionListener(e -> Main.cardLayout.previous(Main.cardPanel));

        switch (level) {
            case 1 -> difficultyLabel.setText("难度：简单");
            case 2 -> difficultyLabel.setText("难度：普通");
            case 3 -> difficultyLabel.setText("难度：困难");
            default -> difficultyLabel.setText("难度：未知");
        }
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

}
