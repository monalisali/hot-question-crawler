package dao;

import entity.HotWord;
import entity.TopCategory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {
    private final SqlSessionFactory sqlSessionFactory;

    public Dao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public TopCategory selectTopCategoryID(String id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            return session.selectOne("com.hcsp.Mapper.selectTopCategoryID", map);
        }
    }

    public List<HotWord> selectAllHotWords(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.hcsp.Mapper.selectAllHotWord");
        }
    }
}
