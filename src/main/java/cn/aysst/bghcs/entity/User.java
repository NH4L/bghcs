package cn.aysst.bghcs.entity;

/**
 * @author lcy
 * @version 2019-12-9
 * 用户实体类
 */
public class User {
    private int userId;
    private String userName;
    private String userOpenId;
    private String userGender;
    private String userProvince;
    private String userCity;
    private String userAvatarUrl;

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }
    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserGender() {
        return userGender;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }
    public String getUserProvince() {
        return userProvince;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }
    public String getUserCity() {
        return userCity;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }
    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }
}
