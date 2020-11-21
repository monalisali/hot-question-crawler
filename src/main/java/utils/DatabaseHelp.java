package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseHelp {
    private static Logger logger = Logger.getLogger(DatabaseHelp.class);
    public static SqlSessionFactory getSqlSessionFactory() {
        String resource = "db/mybatis/config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("getSqlSessionFactory() error:",e);
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
