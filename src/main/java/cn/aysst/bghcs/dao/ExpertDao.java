package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lcy
 * @version 2019-12-12
 */
@Repository("ExpertService")
public class ExpertDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insertExpert(Expert expert) {
        String insertSql = "INSERT INTO expert(expertName, expertEducation, expertJobTitle)\n" +
                "VALUES (?, ?, ?)";
        int count = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"expertId"});
                preparedStatement.setString(1, expert.getExpertName());
                preparedStatement.setString(2, expert.getExpertEducation());
                preparedStatement.setString(3, expert.getExpertJobTitle());
                return preparedStatement;
            }
        });

        if (count > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

}
