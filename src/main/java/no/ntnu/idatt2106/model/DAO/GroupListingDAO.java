package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.group_listing")
public class GroupListingDAO {
    @Id
    private long groupID;


    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }


}
