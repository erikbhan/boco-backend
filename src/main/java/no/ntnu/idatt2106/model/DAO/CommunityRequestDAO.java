package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "Community_request", schema = "public")
public class CommunityRequestDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Community_request_id")
    private int communityRequestID;
    @Column(name= "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDAO userID;
    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityDAO communityID;

    public int getCommunityRequestID() {
        return communityRequestID;
    }

    public void setCommunityRequestID(int communityRequestID) {
        this.communityRequestID = communityRequestID;
    }

    public UserDAO getUserID() {
        return userID;
    }

    public void setUserID(UserDAO userID) {
        this.userID = userID;
    }

    public CommunityDAO getCommunityID() {
        return communityID;
    }

    public void setCommunityID(CommunityDAO communityID) {
        this.communityID = communityID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}