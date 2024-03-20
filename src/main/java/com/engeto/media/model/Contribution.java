package com.engeto.media.model;

public class Contribution {
    private String contributionText;
    private Integer contributionId;
    private Boolean isVisible;
    private String user;


    public Contribution(String contributionText, Integer contributionId, Boolean isVisible, String user) {
        this.contributionText = contributionText;
        this.contributionId = contributionId;
        this.isVisible = isVisible;
        this.user = user;
    }

    public String getContributionText() {
        return contributionText;
    }

    public void setContributionText(String contributionText) {
        this.contributionText = contributionText;
    }

    public Integer getContributionId() {
        return contributionId;
    }

    public void setContributionId(int contributionId) {
        this.contributionId = contributionId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
