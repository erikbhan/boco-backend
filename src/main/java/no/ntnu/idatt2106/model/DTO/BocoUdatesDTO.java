package no.ntnu.idatt2106.model.DTO;

public class BocoUdatesDTO {
    private String date;
    private String user;

    public BocoUdatesDTO(String date, String user) {
        this.date = date;
        this.user = user;
    }

    public BocoUdatesDTO() {
        
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
