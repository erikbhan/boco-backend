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
    @JoinColumn(name = "user_id", nullable = false)
    private UserDAO user;
    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityDAO community;

    public int getCommunityRequestID() {
        return communityRequestID;
    }

    public void setCommunityRequestID(int communityRequestID) {
        this.communityRequestID = communityRequestID;
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO userID) {
        this.user = userID;
    }

    public CommunityDAO getCommunity() {
        return community;
    }

    public void setCommunity(CommunityDAO communityID) {
        this.community = communityID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}