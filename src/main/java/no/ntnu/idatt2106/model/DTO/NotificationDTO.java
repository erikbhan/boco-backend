package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.NotificationDAO;

public class NotificationDTO {
    private int notificationID;
    private boolean isSeen;
    private long createdTime;
    private int communityRequestID;
    private int chatMessageID;

    public  NotificationDTO() {

    }

    public NotificationDTO(boolean isSeen, long createdTime, int communityRequestID) {
        this.isSeen = isSeen;
        this.createdTime = createdTime;
        this.communityRequestID = communityRequestID;
    }

    public NotificationDTO(boolean isSeen, int chatMessageID , long createdTime) {
        this.isSeen = isSeen;
        this.createdTime = createdTime;
        this.chatMessageID = chatMessageID;
    }

    public NotificationDTO(NotificationDAO dao){
        this.isSeen = dao.isSeen();
        this.createdTime = dao.getCreatedTime();
        this.communityRequestID = dao.getCommunityRequest().getCommunityRequestID();
        this.chatMessageID = dao.getChatMessage().getMessageID();
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

    public int getCommunityRequestID() {
        return communityRequestID;
    }

    public void setCommunityRequestID(int communityRequestID) {
        this.communityRequestID = communityRequestID;
    }

    public int getChatMessageID() {
        return chatMessageID;
    }

    public void setChatMessageID(int chatMessageID) {
        this.chatMessageID = chatMessageID;
    }
}
