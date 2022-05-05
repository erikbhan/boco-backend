package no.ntnu.idatt2106.model.DAO;

import org.hibernate.annotations.Check;

import javax.persistence.*;

/**
 * This class functions as a representation of the table notification in the DB.
 * All fields in the notification table is represented in this class, with access methods for everyone.
 */
@Entity
@Table(name = "notification", schema = "public")
public class NotificationDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationID;
    @Column(name= "is_seen")
    private boolean isSeen = false;
    @Column(name= "created_time")
    private long createdTime = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "chat_message", nullable = true)
    private ChatMessageDAO chatMessage;
    @ManyToOne
    @JoinColumn(name = "community_request", nullable = true)
    private CommunityRequestDAO communityRequest;

    public NotificationDAO() {

    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public CommunityRequestDAO getCommunityRequest() {
        return communityRequest;
    }

    public void setCommunityRequest(CommunityRequestDAO communityRequest) {
        this.communityRequest = communityRequest;
    }

    public ChatMessageDAO getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessageDAO chatMessageDAO) {
        this.chatMessage = chatMessageDAO;
    }
}