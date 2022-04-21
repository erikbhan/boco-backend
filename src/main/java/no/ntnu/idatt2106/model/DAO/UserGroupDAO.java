package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.UserGroupID;

import javax.persistence.*;

/**
 *Junction table for handling the many to many connection between classes User and Group
 */
@Entity
@Table(name = "public.user_group")
@IdClass(UserGroupID.class)
public class UserGroupDAO {
     @Id
     @ManyToOne
     @JoinColumn(name = "group_id")
     private GroupDAO groupID;
     @Id
     @ManyToOne
     @JoinColumn(name = "user_id")
     private UserDAO userID;

     @Column(name = "is_administrator")
     private boolean isAdministrator;

     public UserGroupDAO(GroupDAO groupID, UserDAO userID, boolean isAdministrator) {
          this.groupID = groupID;
          this.userID = userID;
          this.isAdministrator = isAdministrator;
     }

     public UserGroupDAO() {
     }

     public GroupDAO getGroupID() {
          return groupID;
     }

     public void setGroupID(GroupDAO groupID) {
          this.groupID = groupID;
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
