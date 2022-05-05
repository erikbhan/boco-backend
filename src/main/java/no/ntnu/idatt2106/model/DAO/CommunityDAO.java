package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Community", schema = "public")
public class CommunityDAO {

    public CommunityDAO(String name, String description, int visibility, String location, String picture) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.location = location;
        this.picture = picture;
    }

    public CommunityDAO() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private int communityID;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "visibility")
    private int visibility = 0;
    @Column(name = "location")
    private String location;
    @Column(name = "picture")
    private String picture;
    

    public int getCommunityID() {
        return this.communityID;
    }

    public void setCommunityID(int communityID) {
        this.communityID = communityID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
