package cn.aysst.bghcs.dao;

import cn.aysst.bghcs.entity.Expert;
import cn.aysst.bghcs.entity.Image;
import cn.aysst.bghcs.entity.Statistics;
import cn.aysst.bghcs.util.JsonUtils;
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

    public Map checkImage(String expertEmail, String cropName, String diseaseName, String imageUrl) {
        String querySql = "SELECT *\n" +
                "FROM review, crop, disease\n" +
                "WHERE expertId=(SELECT expertId FROM expert WHERE expertEmail=?) AND\n" +
                "      imageId=(SELECT imageId FROM pdimage WHERE imageUrl=?) AND\n" +
                "\t\t\treview.cropId=crop.cropId AND review.diseaseId=disease.diseaseId";
        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, expertEmail, imageUrl);
        if (result.next()) {
            System.out.println("存在检测记录，执行更新操作");
            String updateSql = "UPDATE review\n" +
                    "SET cropId=(SELECT cropId FROM crop WHERE cropName=?), diseaseId=(SELECT diseaseId FROM disease WHERE diseaseName=?), reviewTime=SYSDATE()\n" +
                    "WHERE expertId=(SELECT expertId FROM expert WHERE expertEmail=?) AND imageId=(SELECT imageId FROM pdimage WHERE imageUrl=?)";
            int updateCount = jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    preparedStatement.setString(1, cropName);
                    preparedStatement.setString(2, diseaseName);
                    preparedStatement.setString(3, expertEmail);
                    preparedStatement.setString(4, imageUrl);
                    return preparedStatement;
                }
            });
            if (updateCount > 0) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String, String> map = new HashMap<String, String>();
                String checkQuerySql = "SELECT cropName, diseaseName, diseaseType, reviewTime\n" +
                        "FROM review, crop, disease\n" +
                        "WHERE expertId=(SELECT expertId FROM expert WHERE expertEmail=?) AND\n" +
                        "      imageId=(SELECT imageId FROM pdimage WHERE imageUrl=?) AND\n" +
                        "\t\t\treview.cropId=crop.cropId AND review.diseaseId=disease.diseaseId";
                SqlRowSet checkResult = jdbcTemplate.queryForRowSet(checkQuerySql, expertEmail, imageUrl);
                if (checkResult.next()) {
                    map.put("cropName", checkResult.getString("cropName"));
                    map.put("diseaseName", checkResult.getString("diseaseName"));
                    map.put("diseaseType", checkResult.getString("diseaseType"));
                    map.put("reviewTime", df.format(checkResult.getTimestamp("reviewTime")));
                    return map;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            System.out.println("不存在检测记录，执行数据插入操作");
            String insertSql = "INSERT INTO review(diseaseId, cropId, expertId, imageId, reviewTime)\n" +
                    "VALUES ((SELECT diseaseId FROM disease WHERE diseaseName=?),\n" +
                    "\t\t\t\t(SELECT cropId FROM crop WHERE cropName=?),\n" +
                    "\t\t\t\t(SELECT expertId FROM expert WHERE expertEmail=?),\n" +
                    "\t\t\t\t(SELECT imageId FROM pdimage WHERE imageUrl=?), sysdate())";
            int insertCount = jdbcTemplate.update(new PreparedStatementCreator() {
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

            if (insertCount > 0) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String, String> map = new HashMap<String, String>();
                String insertQuerySql = "SELECT cropName, diseaseName, diseaseType, reviewTime\n" +
                        "FROM review, crop, disease\n" +
                        "WHERE expertId=(SELECT expertId FROM expert WHERE expertEmail=?) AND\n" +
                        "      imageId=(SELECT imageId FROM pdimage WHERE imageUrl=?) AND\n" +
                        "\t\t\treview.cropId=crop.cropId AND review.diseaseId=disease.diseaseId";
                SqlRowSet insertResult = jdbcTemplate.queryForRowSet(insertQuerySql, expertEmail, imageUrl);
                if (insertResult.next()) {
                    map.put("cropName", insertResult.getString("cropName"));
                    map.put("diseaseName", insertResult.getString("diseaseName"));
                    map.put("diseaseType", insertResult.getString("diseaseType"));
                    map.put("reviewTime", df.format(insertResult.getTimestamp("reviewTime")));
                    return map;
                } else {
                    return null;
                }
            } else {
                return null;
            }
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Image> imageList = new ArrayList<Image>();
        String querySql = "SELECT imageId, imageName, imageUrl, imageUploadTime\n" +
                          "FROM pdimage\n" +
                          "limit ?, ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, (page-1)*pageNum, pageNum);
        while (result.next()) {
            Image image = new Image();
            image.setImageId(result.getInt("imageId"));
            image.setImageName(result.getString("imageName"));
            image.setImageUrl(result.getString("imageUrl"));

            String queryIsCheckSql = "SELECT *\n" +
                    "FROM review\n" +
                    "WHERE imageId=(SELECT imageId\n" +
                    "\t\t\t\t\t\t\tFROM pdimage\n" +
                    "\t\t\t\t\t\t\tWHERE imageUrl=?)";

            SqlRowSet result2 = jdbcTemplate.queryForRowSet(queryIsCheckSql, result.getString("imageUrl"));
            if (result2.next()) {
                image.setIsChecked(Image.IMAGE_CHECK);
            } else {
                image.setIsChecked(Image.IMAGE_UNCHECK);
            }
            image.setImageUploadTime(df.format(result.getTimestamp("imageUploadTime")));
            imageList.add(image);
        }
        return imageList;
    }

    public Expert getExpertInfo(String expertEmail) {
        Expert expert = new Expert();
        String querySql = "SELECT *\n" +
                "FROM expert\n" +
                "WHERE expertEmail=?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(querySql, expertEmail);
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

    public String changeExpertInfo(Expert expert) {
        String updateSql = "UPDATE expert\n" +
                           "SET expertName=?, expertJobTitle=?, expertEducation=?\n" +
                           "WHERE expertEmail=?";
        int count = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql, new String[]{"expertId"});
                preparedStatement.setString(1, expert.getExpertName());
                preparedStatement.setString(2, expert.getExpertJobTitle());
                preparedStatement.setString(3, expert.getExpertEducation());
                preparedStatement.setString(4, expert.getExpertEmail());
                return preparedStatement;
            }
        });

        if (count > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    public String getStatistics(String expertEmail) {
        String cropSql = "SELECT cropName, COUNT(*) AS corpCount\n" +
                "FROM expert, review, crop\n" +
                "WHERE expert.expertEmail=? AND expert.expertId=review.expertId AND review.cropId=crop.cropId\n" +
                "GROUP BY cropName";
        SqlRowSet cropResult = jdbcTemplate.queryForRowSet(cropSql, expertEmail);
        List<JsonObject> cropList = new ArrayList<JsonObject>();
        while (cropResult.next()) {
            JsonObject object = new JsonObject();
            object.addProperty("name", cropResult.getString("cropName"));
            object.addProperty("value", cropResult.getInt("corpCount"));
            cropList.add(object);
        }

        String diseaseSql = "SELECT diseaseName, COUNT(*) AS diseaseCount\n" +
                "FROM expert, review, disease\n" +
                "WHERE expert.expertEmail=? AND expert.expertId=review.expertId AND review.diseaseId=disease.diseaseId\n" +
                "GROUP BY diseaseName";
        SqlRowSet diseaseResult = jdbcTemplate.queryForRowSet(diseaseSql, expertEmail);
        List<JsonObject> diseaseList = new ArrayList<JsonObject>();
        while (diseaseResult.next()) {
            JsonObject object = new JsonObject();
            object.addProperty("name", diseaseResult.getString("diseaseName"));
            object.addProperty("value", diseaseResult.getInt("diseaseCount"));
            diseaseList.add(object);
        }

        Map<String, Object> mapTree = new HashMap<String, Object>();
        mapTree.put("name", "农作物");
        String queryCropNameSql = "SELECT cropName\n" +
                                  "FROM crop";
        SqlRowSet cropTreeResult = jdbcTemplate.queryForRowSet(queryCropNameSql);
        List<Map> cropTreeList = new ArrayList<Map>();
        while (cropTreeResult.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", cropTreeResult.getString("cropName"));

            List<Map> list1 = new ArrayList<Map>();

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", "病害");
            String queryDiseaseNameBingSql = "SELECT diseaseName\n" +
                    "FROM disease WHERE cropId=(SELECT cropId\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t FROM crop\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE cropName=?) AND diseaseType='病害'";
            SqlRowSet diseaseNameBingResult = jdbcTemplate.queryForRowSet(queryDiseaseNameBingSql, cropTreeResult.getString("cropName"));
            List<Map> list2 = new ArrayList<Map>();
            while (diseaseNameBingResult.next()) {
                Map<String, Object> map5 = new HashMap<String, Object>();
                map5.put("name", diseaseNameBingResult.getString("diseaseName"));
                list2.add(map5);
            }
            map2.put("children", list2);
            list1.add(map2);

            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("name", "虫害");
            String queryDiseaseNameChongSql = "SELECT diseaseName\n" +
                    "FROM disease WHERE cropId=(SELECT cropId\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t FROM crop\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE cropName=?) AND diseaseType='虫害'";
            SqlRowSet diseaseNameChongResult = jdbcTemplate.queryForRowSet(queryDiseaseNameChongSql, cropTreeResult.getString("cropName"));
            List<Map> list3 = new ArrayList<Map>();
            while (diseaseNameChongResult.next()) {
                Map<String, Object> map4 = new HashMap<String, Object>();
                map4.put("name", diseaseNameChongResult.getString("diseaseName"));
                list3.add(map4);
            }
            map3.put("children", list3);
            list1.add(map3);


            map.put("children", list1);
            cropTreeList.add(map);
        }
        mapTree.put("children", cropTreeList);

        Statistics statistics = new Statistics();
        statistics.setCropList(cropList);
        statistics.setDiseaseList(diseaseList);
        statistics.setTreeMap(mapTree);
        return JsonUtils.objectToJson(statistics);
    }
}
