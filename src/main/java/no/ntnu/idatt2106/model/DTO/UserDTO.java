package no.ntnu.idatt2106.model.DTO;

public class UserDTO {
    String token;
    String userId;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
