package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
        String querySql = "SELECT *\n" +
                "FROM expert\n" +
                "WHERE expertEmail=?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, expert.getExpertEmail());
        if (!result.next()) {
            String insertSql = "INSERT INTO expert(expertEmail, expertPassword, expertName, expertEducation, expertJobTitle)\n" +
                    "VALUES (?, ?, ?, ?, ?)";
            int count = jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"expertId"});
                    preparedStatement.setString(1, expert.getExpertEmail());
                    preparedStatement.setString(2, expert.getExpertPassword());
                    preparedStatement.setString(3, expert.getExpertName());
                    preparedStatement.setString(4, expert.getExpertEducation());
                    preparedStatement.setString(5, expert.getExpertJobTitle());
                    return preparedStatement;
                }
            });

            if (count > 0) {
                return "success";
            } else {
                return "fail";
            }
        } else {
            return "emailExists";
        }
    }

    public Expert login(String expertEmail, String expertPassword) {
        Expert expert = new Expert();
        String querySql = "SELECT *\n" +
                "FROM expert\n" +
                "WHERE expertEmail=? AND expertPassword=?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, expertEmail, expertPassword);
        if (result.next()) {
            expert.setExpertId(result.getInt("expertId"));
            expert.setExpertEmail(result.getString("expertEmail"));
            expert.setExpertName(result.getString("expertName"));
            expert.setExpertEducation(result.getString("expertEducation"));
            expert.setExpertJobTitle(result.getString("expertJobTitle"));
            return expert;
        } else
            return null;
    }
}
