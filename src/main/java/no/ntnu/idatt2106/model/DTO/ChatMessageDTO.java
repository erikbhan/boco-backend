package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.DAO.ChatMessageDAO;

import java.util.Date;

public class ChatMessageDTO {
    private long id;
    private String content;
    private Date timestamp;
    private int from;
    private int to;

    public ChatMessageDTO(long id, String content, Date timestamp, int from, int to) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
    }

    public ChatMessageDTO(ChatMessageDAO chatMessageDAO) {
        this.id = chatMessageDAO.getMessageID();
        this.content = chatMessageDAO.getText();
        this.timestamp = chatMessageDAO.getTimeSent();
        this.from = chatMessageDAO.getSendingUserID().getUserID();
        this.to = chatMessageDAO.getReceivingUserID().getUserID();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
