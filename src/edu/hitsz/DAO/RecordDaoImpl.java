package edu.hitsz.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordDaoImpl implements RecordDao {

    // 模拟数据库数据
    private List<Record> recordList = new ArrayList<>();
    private final Path filepath;

    public RecordDaoImpl(Path filepath) {
        this.filepath = filepath;
    }

    @Override
    public void addRecord(Record record) {
        recordList.add(record);
        sortRecords();
    }

    @Override
    public void deleteRecords(String name) {
        // TODO
    }

    @Override
    public List<Record> getAllRecords() {
        readRecords();
        return recordList;
    }

    @Override
    public void sortRecords() {
        recordList.sort(Comparator.comparingInt((Record x) -> -x.getScore()));
    }

    @Override
    public void readRecords() {
        cleanRecords();
        try {
            List<String> lines = Files.readAllLines(this.filepath);
            for (String line: lines) {
                var fields = line.split(",");
                Record record =  new Record(fields[0], Integer.parseInt(fields[1]), fields[2]);
                recordList.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveRecord(Record record) {
        addRecord(record);
        printRecords();
        String s = record + "\n";
        try {
            Files.write(this.filepath, s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanRecords() {
        recordList = new ArrayList<>();
    }

    @Override
    public void printRecords() {
        System.out.println("********************************************");
        System.out.println("                 得分排行榜                   ");
        System.out.println("********************************************");
        int rank = 0;
        for (Record record : recordList) {
            rank += 1;
            String s = "第" + rank + "名" + ": " + record + "\n";
            System.out.print(s);
        }
    }
}
