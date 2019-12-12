package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Image;
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
import java.util.List;

/**
 * @author lcy
 * @version 2019-12-9
 */
@Repository("ImageService")
public class ImageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insertImage(List<Image> list, String userOpenId) {
        int successCount = 0;
        for (Image image: list) {
            String insertSql = "INSERT INTO pdimage(userId, imageName, imageUrl, imageUploadTime)\n" +
                    "VALUES ((SELECT userId FROM user WHERE userOpenId=?), ?, ?, sysdate())";
            System.out.println(insertSql);
            int count = jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"imageId"});
                    preparedStatement.setString(1, userOpenId);
                    preparedStatement.setString(2, image.getImageName());
                    preparedStatement.setString(3, image.getImageUrl());
                    return preparedStatement;
                }
            });
            successCount += count;

        }
        if (successCount == list.size()) {
            return "success";
        } else {
            return "fail";
        }
    }

    public List<Image> getUserImage(String userOpenId) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Image> imageList = new ArrayList<Image>();
        String querySql = "SELECT imageId, imageName, imageUrl, imageUploadTime\n" +
                "FROM pdimage\n" +
                "WHERE userId=(SELECT userId\n" +
                "\t\t\t\t\t\t\tFROM user\n" +
                "\t\t\t\t\t\t\tWHERE userOpenId=?)";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, userOpenId);
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
