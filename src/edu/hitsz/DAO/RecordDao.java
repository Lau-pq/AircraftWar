package edu.hitsz.DAO;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;

public interface RecordDao {

    // 获取所有数据
    List<Record> getAllRecords();

    // 将成绩加入榜单
    void addRecord(Record record);

    // 删除数据
    void deleteRecord(int row);
    void deleteRecords(int[] rows);

    // 对文件进行排序
    void sortRecords();

    // 从文件中读取数据
    List<Record> readRecords();

    // 将数据存储到文件
    void saveRecord(Record record);

    // 更新文件中的数据
    void updateRecords(List<Record> records) throws IOException;

    // 清空本地数据库数据
    void cleanLocalRecords();

    // 清空远程数据库数据
    void cleanRemoteRecords();

    // 打印分数
    void printRecords();

    // 转变为 SwingTable 对象
    DefaultTableModel toSwingTable(List<Record> records);
}
