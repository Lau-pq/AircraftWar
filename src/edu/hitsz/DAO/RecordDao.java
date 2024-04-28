package edu.hitsz.DAO;

import java.util.List;

public interface RecordDao {

    // 获取所有数据
    List<Record> getAllRecords();

    // 将成绩加入榜单
    void addRecord(Record record);

    // 删除数据
    void deleteRecords(String name);

    // 对文件进行排序
    void sortRecords();

    // 从文件中读取数据
    void readRecords();

    // 将数据存储到文件
    void saveRecord(Record record);

    // 清空数据库数据
    void cleanRecords();

    // 打印分数
    void printRecords();

}
