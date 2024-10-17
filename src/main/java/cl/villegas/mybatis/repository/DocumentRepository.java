package cl.villegas.mybatis.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cl.villegas.mybatis.model.Document;

@Mapper
public interface DocumentRepository {
    @Delete("DELETE FROM DOCUMENT WHERE ID = #{id}")
    void delete(long id);
    
    @Select("SELECT * FROM DOCUMENT")
    List<Document> findAll();

    @Select("SELECT * FROM DOCUMENT WHERE ID = #{id}")
    Document findById(long id);

    @Insert("INSERT INTO DOCUMENT(NAME, FILE, CONTENTTYPE) VALUES(#{name}, #{file}, #{contentType})")
    void save(Document document);

    @Update("UPDATE DOCUMENT SET NAME = #{name}, FILE = #{file}, CONTENTTYPE = #{contentType} WHERE ID = #{id}")
    void update(Document document);
}