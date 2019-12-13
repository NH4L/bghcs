package cn.aysst.bghcs.entity;

/**
 * @author lcy
 * @version 2019-12-10
 * 病虫害实体类
 */
public class Disease {
    private int diseaseId;
    private String diseaseName;
    private String diseaseSolution;
    private String diseaseType;//两种类型(病害，虫害)

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }
    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseSolution(String diseaseSolution) {
        this.diseaseSolution = diseaseSolution;
    }
    public String getDiseaseSolution() {
        return diseaseSolution;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }
    public String getDiseaseType() {
        return diseaseType;
    }
}
