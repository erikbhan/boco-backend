package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.CommunityDAO;

public class CommunityDTO {
    int communityId;
    private String name;
    private String description;
    private int visibility;
    private String location;
    private String picture;

    public CommunityDTO(String name, String description, int visibility, String location, String picture) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.location = location;
        this.picture = picture;
    }

    public CommunityDTO(CommunityDAO communityDAO){
        this.communityId = communityDAO.getCommunityID();
        this.name = communityDAO.getName();
        this.description = communityDAO.getDescription();
        this.visibility = communityDAO.getVisibility();
        this.location = communityDAO.getLocation();
        this.picture = communityDAO.getPicture();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCommunityId() {return communityId;}

    public void setCommunityId(int communityId) {this.communityId = communityId;}
}
