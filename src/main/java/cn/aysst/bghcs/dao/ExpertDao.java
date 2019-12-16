package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import com.google.gson.JsonObject;
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

    public String checkImage(String expertEmail, String cropName, String diseaseName, String imageUrl) {
        String insertSql = "INSERT INTO review(diseaseId, cropId, expertId, imageId, reviewTime)\n" +
                "VALUES ((SELECT diseaseId FROM disease WHERE diseaseName=?),\n" +
                "\t\t\t\t(SELECT cropId FROM crop WHERE cropName=?),\n" +
                "\t\t\t\t(SELECT expertId FROM expert WHERE expertEmail=?),\n" +
                "\t\t\t\t(SELECT imageId FROM pdimage WHERE imageUrl=?), sysdate())";
        int count = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setString(1, diseaseName);
                preparedStatement.setString(2, cropName);
                preparedStatement.setString(3, expertEmail);
                preparedStatement.setString(4, imageUrl);
                return preparedStatement;
            }
        });

        if (count > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    public Map getCheckedImage(String expertEmail, String imageUrl) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> map = new HashMap<String, String>();
        String querySql = "SELECT cropName, diseaseName, diseaseType, reviewTime\n" +
                "FROM review, crop, disease\n" +
                "WHERE expertId=(SELECT expertId FROM expert WHERE expertEmail=?) AND\n" +
                "      imageId=(SELECT imageId FROM pdimage WHERE imageUrl=?) AND\n" +
                "\t\t\treview.cropId=crop.cropId AND review.diseaseId=disease.diseaseId";
        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, expertEmail, imageUrl);
        if (result.next()) {
            map.put("cropName", result.getString("cropName"));
            map.put("diseaseName", result.getString("diseaseName"));
            map.put("diseaseType", result.getString("diseaseType"));
            map.put("reviewTime", df.format(result.getTimestamp("reviewTime")));
            return map;
        } else {
            return null;
        }
    }

    public List<Image> getAllImage(int page, int pageNum) {
        int rpage = page * pageNum;
        int lpage = (page-1) * pageNum;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Image> imageList = new ArrayList<Image>();
        String querySql = "SELECT imageId, imageName, imageUrl, imageUploadTime\n" +
                          "FROM pdimage\n" +
                          "limit ?, ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, lpage, rpage);
        while (result.next()) {
            Image image = new Image();
            image.setImageId(result.getInt("imageId"));
            image.setImageName(result.getString("imageName"));
            image.setImageUrl(result.getString("imageUrl"));
            image.setImageUploadTime(df.format(result.getTimestamp("imageUploadTime")));
            imageList.add(image);
        }
        return imageList;
    }
}
