package cn.aysst.bghcs.entity;

/**
 * @author lcy
 * @version 2019-12-10
 * 农作物实体类
 */
public class Crop {
    private int cropId;
    private String cropName;

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }
    public int getCropId() {
        return cropId;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
    public String getCropName() {
        return cropName;
    }
}
