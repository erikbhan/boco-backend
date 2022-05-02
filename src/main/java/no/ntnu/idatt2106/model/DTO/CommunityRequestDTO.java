package no.ntnu.idatt2106.model.DTO;

public class CommunityRequestDTO {
    public CommunityRequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public CommunityRequestDTO() {
    }
}
