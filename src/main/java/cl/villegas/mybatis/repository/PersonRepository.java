package cl.villegas.mybatis.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cl.villegas.mybatis.model.Person;

@Mapper
public interface PersonRepository {
    @Delete("DELETE FROM PERSON WHERE ID = #{id}")
    void delete(long id);

    @Select("SELECT * FROM PERSON")
    @Results({ @Result(id = true, column = "ID", property = "id"), @Result(column = "RUT", property = "rut"),
            @Result(column = "NAMES", property = "names"), @Result(column = "SURNAMES", property = "surnames"),
            @Result(column = "AGE", property = "age") })
    List<Person> findAll();

    @Select(value = "SELECT * FROM PERSON WHERE ID = #{id}")
    @Options(statementType = StatementType.CALLABLE)
    @ResultType(Person.class)
    Person findById(long id);

    @Insert("INSERT INTO PERSON(RUT, NAMES, SURNAMES, AGE) VALUES(#{rut, typeHandler = cl.villegas.mybatis.typehandler.RutTypeHandler}, #{names}, #{surnames}, #{age})")
    void save(Person person);

    @Update("UPDATE PERSON SET RUT = #{rut}, NAMES = #{names}, SURNAMES = #{surnames}, AGE = #{age} WHERE ID = #{id}")
    void update(Person person);
}