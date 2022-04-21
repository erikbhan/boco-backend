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
    private long communityID;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "visibility")
    private int visibility;
    @Column(name = "location")
    private String location;
    @Column(name = "picture")
    private String picture;
    
}
