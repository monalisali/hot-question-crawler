package dao;

import entity.*;
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

    //TopCategory

    public List<TopCategory> selectAllActiveTopCategories(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.hcsp.Mapper.selectAllActiveTopCategories");
        }
    }

    //HotWord
    public List<HotWord> selectHotWordsByTopCategoryId (String topCategoryId){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> map = new HashMap<>();
            map.put("topCategoryId", topCategoryId);
            return session.selectList("com.hcsp.Mapper.selectHotWordsByTopCategoryId",map);
        }
    }

    public HotWord selectHotWordByName(String name){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            return session.selectOne("com.hcsp.Mapper.selectHotWordByName", map);
        }
    }

    public void updateHotWord(HotWord hotWord){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            session.update("com.hcsp.Mapper.updateHotWord",hotWord);
        }
    }

    //Question
    public void batchInsertQuestions(List<Question> questions){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String, Object> map = new HashMap<>();
            map.put("questions", questions);
            session.insert("com.hcsp.Mapper.insertQuestions",map);
        }
    }

    public List<Question> selectQuestionsByHotWordId (String hotWordId){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String, Object> map = new HashMap<>();
            map.put("hotWordId", hotWordId);
            return session.selectList("com.hcsp.Mapper.selectQuestionsByHotWordId",map);
        }
    }

    public List<Question> selectQuestionByTopCategory(String topCategoryId){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String,Object> map = new HashMap<>();
            map.put("topCategoryId",topCategoryId);
            return session.selectList("com.hcsp.Mapper.selectQuestionByTopCategory",map);
        }
    }

    //Xzse86
    public XZSE86 selectXzse86ByTopCategoryId (String topCategoryID){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String,Object> map = new HashMap<>();
            map.put("topCategoryID",topCategoryID);
            return session.selectOne("com.hcsp.Mapper.selectXzse86ByTopCategoryId",map);
        }
    }

    //CombinedQuestion
    public List<CombinedQuestion> selectCombinedQuestion (String topCategoryId){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String,Object> map = new HashMap<>();
            map.put("topCategoryId",topCategoryId);
            map.put("hotWordId",null);
            return session.selectList("com.hcsp.Mapper.selectCombinedQuestion",map);
        }
    }

    public void insertCombinedQuestion(CombinedQuestion cq){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Map<String,Object> map = new HashMap<>();
            map.put("id",cq.getId());
            map.put("topCategoryId",cq.getTopCategoryID());
            map.put("hotWordId",cq.getHotWordId());
            map.put("url",cq.getUrl());
            map.put("name",cq.getName());
            map.put("createTime",cq.getCreateTime());
            session.insert("com.hcsp.Mapper.insertCombinedQuestion",map);
        }
    }
}
