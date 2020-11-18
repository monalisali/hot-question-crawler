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

    public List<TopCategory> selectAllActiveTopCategories(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.hcsp.Mapper.selectAllActiveTopCategories");
        }
    }

    public List<HotWord> selectHotWordsByTopCategoryId (String topCategoryId){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> map = new HashMap<>();
            map.put("topCategoryId", topCategoryId);
            return session.selectList("com.hcsp.Mapper.selectHotWordsByTopCategoryId",map);
        }
    }
}
