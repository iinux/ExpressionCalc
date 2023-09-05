package cn.iinux.java.alpha.springboot;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MybatisUtil {
    private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
    private static SqlSessionFactory sqlSessionFactory;

    public void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getSqlSession() {
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession == null) {
            if (sqlSessionFactory == null) {
                init();
            }
            sqlSession = sqlSessionFactory.openSession();
            threadLocal.set(sqlSession);
        }

        Connection conn = sqlSession.getConnection();
        System.out.println(conn != null ? "连接成功" : "连接失败");
    }

    /**
     * 关闭SqlSession与当前线程分开
     */
    public static void closeSqlSession() {
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession != null) {
            sqlSession.close();
            //分开当前线程与SqlSession对象的关系，目的是让GC尽早回收
            threadLocal.remove();
        }
    }

    @Test
    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();
        // 指定 逆向工程配置文件
        File configFile = new File("src/main/resources/generatorConfig.xml");
        // File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
