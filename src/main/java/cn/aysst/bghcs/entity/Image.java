package cn.aysst.bghcs.entity;

import java.sql.Time;
import java.util.Date;

/**
 * @author lcy
 * @version 2019-12-9
 * 图片实体类
 */
public class Image {
    public static final int IMAGE_CHECK = 1;
    public static final int IMAGE_UNCHECK = -1;
    private int imageId;
    private String imageUrl;
    private String imageName;
    private String imageUploadTime;
    private int isChecked;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getImageName() {
        return imageName;
    }

    public void setImageUploadTime(String imageUploadTime) {
        this.imageUploadTime = imageUploadTime;
    }
    public String getImageUploadTime() {
        return imageUploadTime;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
    public int getIsChecked() {
        return isChecked;
    }
}
