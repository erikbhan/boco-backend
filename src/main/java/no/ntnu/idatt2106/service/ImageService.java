package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ImageDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.ImageRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class for handling image related operations
 */
@Service
public class ImageService {
    private ImageRepository _imageRepository;
    private UserRepository _userRepository;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        _imageRepository = imageRepository;
        _userRepository = userRepository;
    }

    /**
     * Method to get the image
     * @param imageID the id of the image
     * @return the image
     */
    public byte[] getImage(int imageID) {
        Optional<ImageDAO> image = _imageRepository.findById(imageID);
        if(image.isEmpty()) return null;
        return image.get().getImage();
    }

    /**
     * Method to add a new image
     * @param image the image to be added
     * @param userID the id of the user
     * @return id of the image that was added
     */
    public int addImage(byte[] image, int userID) {
        UserDAO user = _userRepository.getById(userID);
        if (_imageRepository.findImageDAOByUserAndImage(user, image) != null){
            return _imageRepository.findImageDAOByUserAndImage(user, image).getImageId();
        }
        ImageDAO imageDAO = new ImageDAO();
        imageDAO.setImage(image);
        imageDAO.setUser(user);
        ImageDAO savedImg = _imageRepository.save(imageDAO);
        return savedImg.getImageId();
    }

    /**
     * Method that deletes a image
     * @param imageID the id of the image
     * @return true if the image was deleted
     */
    public boolean deleteImage(int imageID) {
        Optional<ImageDAO> image = _imageRepository.findById(imageID);
        if(image.isEmpty()) return false;
        _imageRepository.delete(image.get());
        return true;
    }

    /**
     * Method to check if the user has permission to manage the image
     * @param imageID the id of the image
     * @param userID the id of the user
     * @return true if the user has permission to manage the image
     */
    public boolean ownsImage(int imageID, int userID) {
        Optional<ImageDAO> image = _imageRepository.findById(imageID);
        if(image.isEmpty()) return false;
        return image.get().getUser().getUserID() == userID;
    }
}
