package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map> getCheckInfo(String userOpenId, String imageUrl) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map> resultList = new ArrayList<Map>();
        String querySql = "SELECT expertName, expertEmail, cropName, diseaseName, diseaseSolution, diseaseType, reviewTime\n" +
                          "FROM expert, review, crop, disease\n" +
                          "WHERE expert.expertId=review.expertId AND\n" +
                          "      review.cropId=crop.cropId AND\n" +
                          "\t\t\treview.diseaseId=disease.diseaseId AND\n" +
                          "\t\t\treview.imageId=(SELECT imageId\n" +
                          "\t\t\t\t\t\t\t\t\t\t\tFROM pdimage\n" +
                          "\t\t\t\t\t\t\t\t\t\t\tWHERE imageUrl=? AND\n" +
                          "\t\t\t\t\t\t\t\t\t\t\t\t\t\tuserId=(SELECT userId\n" +
                          "\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM user\n" +
                          "\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE userOpenId=?))";
        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, imageUrl, userOpenId);
        while (result.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("expertName", result.getString("expertName"));
            map.put("expertEmail", result.getString("expertEmail"));
            map.put("cropName", result.getString("cropName"));
            map.put("diseaseName", result.getString("diseaseName"));
            map.put("diseaseSolution", result.getString("diseaseSolution"));
            map.put("diseaseType", result.getString("diseaseType"));
            map.put("reviewTime", df.format(result.getTimestamp("reviewTime")));
            resultList.add(map);
        }
        return resultList;
    }
}
