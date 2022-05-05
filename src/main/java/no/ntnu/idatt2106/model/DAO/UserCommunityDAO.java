package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.UserCommunityID;

import javax.persistence.*;

/**
 *Junction table for handling the many to many connection between classes User and Community
 */
@Entity
@Table(name = "user_community", schema = "public")
@IdClass(UserCommunityID.class)
public class UserCommunityDAO {
     @Id
     @ManyToOne
     @JoinColumn(name = "community_id")
     private CommunityDAO communityID;
     @Id
     @ManyToOne
     @JoinColumn(name = "user_id")
     private UserDAO userID;

     @Column(name = "is_administrator")
     private boolean isAdministrator = false;

     public UserCommunityDAO(CommunityDAO community, UserDAO user, boolean isAdministrator) {
          this.communityID = community;
          this.userID = user;
          this.isAdministrator = isAdministrator;
     }

     public UserCommunityDAO() {
     }

     public CommunityDAO getCommunity() {
          return communityID;
     }

     public void setCommunity(CommunityDAO communityID) {
          this.communityID = communityID;
     }

     public UserDAO getUser() {
          return userID;
     }

     public void setUser(UserDAO userID) {
          this.userID = userID;
     }

     public boolean isAdministrator() {
          return isAdministrator;
     }

     public void setAdministrator(boolean administrator) {
          isAdministrator = administrator;
     }
}
