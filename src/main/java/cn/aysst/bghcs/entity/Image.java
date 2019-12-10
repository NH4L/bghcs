package cn.aysst.bghcs.entity;

/**
 * @author lcy
 * @version 2019-12-9
 * 图片实体类
 */
public class Image {
    private int imageId;
    private String imageAddr;
    private String imageName;

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }
    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getImageName() {
        return imageName;
    }
}
