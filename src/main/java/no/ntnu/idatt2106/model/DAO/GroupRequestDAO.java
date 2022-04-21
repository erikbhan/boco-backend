package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "public.Group_request")
public class GroupRequestDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_request_id")
    private long groupRequestID;
    @Column(name= "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDAO userID;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupDAO groupID;
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationDAO notificationID;

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