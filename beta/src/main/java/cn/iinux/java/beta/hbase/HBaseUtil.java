package cn.iinux.java.beta.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HBaseUtil {
    public static String TABLE = "ns1:t1";

    static Map<String, Table> tableMap = new ConcurrentHashMap<>();

    static Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection != null) {
            return connection;
        }

        synchronized (HBaseUtil.class) {
            if (connection != null) {
                return connection;
            }

            String quorum = "127.0.0.1";
            Configuration conf = new Configuration();
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.zookeeper.quorum", quorum);
            conf.set("hbase.rpc.timeout", "2000");
            conf.set("hbase.client.retries.number", "15");
            conf.set("hbase.client.pause", "100");
            conf.set("hbase.client.operation.timeout", "20000");

            connection = ConnectionFactory.createConnection(conf);
        }

        return connection;
    }

    public static Table getTable(String tableName) throws Exception {
        Table table = tableMap.get(tableName);
        if (table != null) {
            return table;
        }

        synchronized (HBaseUtil.class) {
            Table table1 = tableMap.get(tableName);
            if (table1 != null) {
                return table1;
            }

            table = getConnection().getTable(TableName.valueOf(tableName));
            tableMap.put(tableName, table);
        }

        return table;
    }

    public static void main(String[] args) throws Exception {
        Table table = getTable(TABLE);
        Result data = table.get(new Get("a".getBytes()));
        System.out.println(data);
    }
}
