package no.ntnu.idatt2106.model.DTO;

import no.ntnu.idatt2106.model.enums.ChatStatusEnum;

public class ChatStatusDTO {
    private ChatStatusEnum status;
    private int from;
    private int to;
    private String id;

    public ChatStatusDTO(ChatStatusEnum status, int from, int to, String id) {
        this.status = status;
        this.from = from;
        this.to = to;
        this.id = id;
    }

    public ChatStatusDTO() {}

    public ChatStatusEnum getStatus() {
        return status;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getId() {
        return id;
    }

    public void setStatus(ChatStatusEnum status) {
        this.status = status;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setId(String id) {
        this.id = id;
    }

}