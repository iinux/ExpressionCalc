package cn.iinux.java.alpha;

import com.aliyun.hitsdb.client.HiTSDB;
import com.aliyun.hitsdb.client.HiTSDBClientFactory;
import com.aliyun.hitsdb.client.HiTSDBConfig;
import com.aliyun.hitsdb.client.value.request.Point;
import org.junit.Test;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws Exception {
//        WordCount.main(args);
//        JavaLin.main(args);
//        ExpressionCalculation.main(args);
//        JNA.main(args);
        System.out.println("Hello World1!");
    }

    @Test
    public void hiTsdb() throws InterruptedException, IOException {
        // 创建 HiTSDB 对象
        HiTSDBConfig config = HiTSDBConfig.address("www.baidu.com", 3242).config();
        HiTSDB tsdb = HiTSDBClientFactory.connect(config);
        // 构造数据并写入 HiTSDB
        for (int i = 0; i < 3600; i++) {
            long timestamp = System.currentTimeMillis();
            Point point = Point.metric("test").tag("V", "1.0").value(timestamp, 123.4567).build();
            System.out.println("Hello World!!");
            Thread.sleep(1000);  // 1秒提交1次
            tsdb.put(point);
        }
        // 安全关闭客户端，以防数据丢失。
        System.out.println("关闭");
        tsdb.close();
    }
}
