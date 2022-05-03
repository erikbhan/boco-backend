package no.ntnu.idatt2106.model.DAO;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "public")
public class ImageDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private int imageId;

    @Lob
    @Column(length=16777215, name = "image")
    private byte[] image;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserDAO user;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

}
