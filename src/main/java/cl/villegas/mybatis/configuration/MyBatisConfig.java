package cl.villegas.mybatis.configuration;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cl.villegas.mybatis.repository")
public class MyBatisConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean.getObject();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
}