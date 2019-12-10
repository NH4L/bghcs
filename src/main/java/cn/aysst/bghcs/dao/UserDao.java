package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.User;
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
 * @version 2019-12-9
 */
@Repository("UserService")
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insertUser(User user) {
        String querySql = "SELECT *\n" +
                          "FROM user\n" +
                          "WHERE userOpenId=?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, user.getUserOpenId());
        if (!result.next()) {
            String insertSql = "INSERT INTO user(userName, userOpenId, userGender, userProvince, userCity, userAvatarUrl)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            int count = jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"userId"});
                    preparedStatement.setString(1, user.getUserName());
                    preparedStatement.setString(2, user.getUserOpenId());
                    preparedStatement.setString(3, user.getUserGender());
                    preparedStatement.setString(4, user.getUserProvince());
                    preparedStatement.setString(5, user.getUserCity());
                    preparedStatement.setString(6, user.getUserAvatarUrl());
                    return preparedStatement;
                }
            });

            if (count > 0) {
                return "success";
            } else {
                return "fail";
            }
        } else {
            return "fail";
        }
    }

    public String checkOpenId(String userOpenId) {
        String querySql = "SELECT *\n" +
                "FROM user\n" +
                "WHERE userOpenId=?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, userOpenId);
        if (result.next()) {
            return "success";
        } else {
            return "fail";
        }
    }
}
