package cn.iinux.java.beta.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseReader {

    public static void main(String[] args) throws IOException {
        // 创建 HBase 配置
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost"); // 替换为您的 ZooKeeper 服务器地址

        // 创建 HBase 连接
        Connection connection = ConnectionFactory.createConnection(config);

        // 获取 HBase 表
        TableName tableName = TableName.valueOf("your_table_name"); // 替换为您的表名
        Table table = connection.getTable(tableName);

        // 创建 Get 请求
        Get get = new Get(Bytes.toBytes("your_row_key")); // 替换为您的行键

        // 执行 Get 请求并获取结果
        Result result = table.get(get);

        // 处理结果
        for (Cell cell : result.listCells()) {
            byte[] family = CellUtil.cloneFamily(cell);
            byte[] qualifier = CellUtil.cloneQualifier(cell);
            byte[] value = CellUtil.cloneValue(cell);

            System.out.println("Family: " + Bytes.toString(family));
            System.out.println("Qualifier: " + Bytes.toString(qualifier));
            System.out.println("Value: " + Bytes.toString(value));
        }

        // 关闭连接
        table.close();
        connection.close();
    }
}
