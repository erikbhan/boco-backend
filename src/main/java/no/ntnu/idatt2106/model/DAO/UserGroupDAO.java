package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "public.user_group")
public class UserGroupDAO {
     @Id
     private long userID;

     public long getUserID() {
          return userID;
     }

     public void setUserID(long userID) {
          this.userID = userID;
     }


}
