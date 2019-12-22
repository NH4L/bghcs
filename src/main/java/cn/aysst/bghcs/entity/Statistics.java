package cn.aysst.bghcs.entity;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class Statistics {
    private List<JsonObject> cropList;
    private List<JsonObject> diseaseList;
    private Map treeMap;

    public void setCropList(List<JsonObject> cropList) {
        this.cropList = cropList;
    }
    public List<JsonObject> getCropList() {
        return cropList;
    }

    public void setDiseaseList(List<JsonObject> diseaseList) {
        this.diseaseList = diseaseList;
    }
    public List<JsonObject> getDiseaseList() {
        return diseaseList;
    }

    public void setTreeMap(Map treeMap) {
        this.treeMap = treeMap;
    }
    public Map getTreeMap() {
        return treeMap;
    }
}
