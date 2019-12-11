package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

}
