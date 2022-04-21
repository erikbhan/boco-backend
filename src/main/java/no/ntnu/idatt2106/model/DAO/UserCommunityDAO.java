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
     private boolean isAdministrator;

     public UserCommunityDAO(CommunityDAO communityID, UserDAO userID, boolean isAdministrator) {
          this.communityID = communityID;
          this.userID = userID;
          this.isAdministrator = isAdministrator;
     }

     public UserCommunityDAO() {
     }

     public CommunityDAO getCommunityID() {
          return communityID;
     }

     public void setCommunityID(CommunityDAO communityID) {
          this.communityID = communityID;
     }

     public UserDAO getUserID() {
          return userID;
     }

     public void setUserID(UserDAO userID) {
          this.userID = userID;
     }

     public boolean isAdministrator() {
          return isAdministrator;
     }

     public void setAdministrator(boolean administrator) {
          isAdministrator = administrator;
     }
}
