package no.ntnu.idatt2106.model.DTO;

public class CommunityRequestDTO {

    String message;

    public CommunityRequestDTO(String message) {
        this.message = message;
    }

    public CommunityRequestDTO() {
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
