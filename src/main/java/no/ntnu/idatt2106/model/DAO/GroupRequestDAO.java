package no.ntnu.idatt2106.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.Group_request")
public class GroupRequestDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_request_id")
    private long groupRequestID;
    @Column(name= "text")
    private String text;

    public long getGroupRequestID() {
        return this.groupRequestID;
    }

    public void setGroupRequestID(long groupRequestID) {
        this.groupRequestID = groupRequestID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}