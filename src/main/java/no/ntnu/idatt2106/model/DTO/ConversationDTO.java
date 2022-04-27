package no.ntnu.idatt2106.model.DTO;

public class ConversationDTO {
    private ChatMessageDTO lastMessage;
    private PublicUserDTO recipient;

    public ConversationDTO(ChatMessageDTO lastMessage, PublicUserDTO recipient) {
        this.lastMessage = lastMessage;
        this.recipient = recipient;
    }

    public ChatMessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatMessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }

    public PublicUserDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(PublicUserDTO recipient) {
        this.recipient = recipient;
    }
}
