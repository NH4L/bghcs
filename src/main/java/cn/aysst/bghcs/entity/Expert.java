package cn.aysst.bghcs.entity;

public class Expert {
    private int expertId;
    private String expertEmail;
    private String expertPassword;
    private String expertName;
    private String expertEducation;
    private String expertJobTitle;

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }
    public int getExpertId() {
        return expertId;
    }

    public void setExpertEmail(String expertEmail) {
        this.expertEmail = expertEmail;
    }
    public String getExpertEmail() {
        return expertEmail;
    }

    public void setExpertPassword(String expertPassword) {
        this.expertPassword = expertPassword;
    }
    public String getExpertPassword() {
        return expertPassword;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }
    public String getExpertName() {
        return expertName;
    }

    public void setExpertEducation(String expertEducation) {
        this.expertEducation = expertEducation;
    }
    public String getExpertJobTitle() {
        return expertJobTitle;
    }

    public void setExpertJobTitle(String expertJobTitle) {
        this.expertJobTitle = expertJobTitle;
    }
    public String getExpertEducation() {
        return expertEducation;
    }
}
