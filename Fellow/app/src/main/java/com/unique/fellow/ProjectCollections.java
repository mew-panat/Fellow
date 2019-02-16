package com.unique.fellow;

import java.util.ArrayList;

public class ProjectCollections {
    private String projectID;
    private String projectName;
    private String projectDescription;
    private String category;
    private String teamName;
    private ArrayList<String> members;

    public ProjectCollections(){

    }

    public ProjectCollections(String projectName, String projectDescription, String category, String teamName, ArrayList<String> members) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.category = category;
        this.teamName = teamName;
        this.members = members;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String > members) {
        this.members = members;
    }
}
