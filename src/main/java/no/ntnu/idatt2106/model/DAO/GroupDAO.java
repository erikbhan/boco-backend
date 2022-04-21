package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.Group")
public class GroupDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupID;
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
