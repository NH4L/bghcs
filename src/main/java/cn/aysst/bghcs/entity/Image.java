package cn.aysst.bghcs.entity;

/**
 * @author lcy
 * @version 2019-12-9
 * 图片实体类
 */
public class Image {
    private int imageId;
    private String imageUrl;
    private String imageName;

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
}
