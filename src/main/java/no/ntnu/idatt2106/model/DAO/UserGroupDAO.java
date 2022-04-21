package no.ntnu.idatt2106.model.DAO;

import no.ntnu.idatt2106.model.ID.UserGroupID;

import javax.persistence.*;

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
}
