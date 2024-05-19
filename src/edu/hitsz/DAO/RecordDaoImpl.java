package edu.hitsz.DAO;

import edu.hitsz.Game.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordDaoImpl implements RecordDao {

    // 模拟数据库数据
    private List<Record> records = new ArrayList<>();
    private final Path filepath;

    public RecordDaoImpl(int level) {
        switch (level) {
            case 1 -> this.filepath = Path.of("src/database/easyRecord.txt");
            case 2 -> this.filepath = Path.of("src/database/mediumRecord.txt");
            case 3 -> this.filepath = Path.of("src/database/hardRecord.txt");
            default -> this.filepath = null;
        }
    }

    @Override
    public void addRecord(Record record) {
        records.add(record);
    }

    @Override
    public void deleteRecords(int[] rows) {
        records = readRecords();
        for (int i = rows.length - 1; i >= 0; i--) {
            records.remove(rows[i]);
        }
        updateRecords(records);
    }

    @Override
    public List<Record> getAllRecords() {
        records = readRecords();
        return records;
    }

    @Override
    public void sortRecords() {
        records.sort(Comparator.comparingInt((Record x) -> -x.getScore()));
    }

    @Override
    public List<Record> readRecords() {
        cleanLocalRecords();
        if (Files.exists(this.filepath)) {
            try {
                List<String> lines = Files.readAllLines(this.filepath);
                for (String line : lines) {
                    var fields = line.split(",");
                    Record record = new Record(fields[0], Integer.parseInt(fields[1]), fields[2]);
                    records.add(record);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sortRecords();
        }
        return records;
    }

    @Override
    public void saveRecord(Record record) {
        if (record.getName() != null) {
            addRecord(record);
            sortRecords();
            printRecords();
            String s = record + "\n";
            try {
                Files.write(this.filepath, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateRecords(List<Record> records) {
        cleanRemoteRecords();
        for (Record record : records) {
            String s = record + "\n";
            try {
                Files.write(this.filepath, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cleanLocalRecords() {
        records = new ArrayList<>();
    }

    @Override
    public void cleanRemoteRecords() {
        try {
            Files.write(this.filepath, "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printRecords() {
        System.out.println("********************************************");
        System.out.println("                 得分排行榜                   ");
        System.out.println("********************************************");
        int rank = 0;
        for (Record record : records) {
            rank += 1;
            String s = "第" + rank + "名" + ": " + record + "\n";
            System.out.print(s);
        }
    }

    @Override
    public DefaultTableModel toSwingTable(List<Record> records) {
        String[] columnName = {"排名", "姓名", "分数", "时间"};
        String[][] tableData;
        int size = records.size();
        tableData = new String[size][4];
        for (int i = 0; i < size; i++) {
            Record record = records.get(i);
            tableData[i][0] = String.valueOf(i + 1);
            tableData[i][1] = record.getName();
            tableData[i][2] = String.valueOf(record.getScore());
            tableData[i][3] = record.getTime();
        }
        return new DefaultTableModel(tableData, columnName);
    }
}
