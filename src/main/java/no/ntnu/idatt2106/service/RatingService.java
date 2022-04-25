package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DAO.RatingDAO;
import no.ntnu.idatt2106.model.DAO.RentDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RatingDTO;
import no.ntnu.idatt2106.repository.RatingRepository;
import no.ntnu.idatt2106.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A class meant to use the access-point made to the Rating table in the DB.
 * This class uses methods from the {@link no.ntnu.idatt2106.repository.RatingRepository RatingRepository}
 * and the {@link no.ntnu.idatt2106.repository.RentRepository RentRepository}
 */
@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RentRepository rentRepository;
    private final UserService userService;
    private final ListingService listingService;
    private final RentService rentService;

    public RatingService(RatingRepository ratingRepository, RentRepository rentRepository, UserService userService, ListingService listingService, RentService rentService) {
        this.ratingRepository = ratingRepository;
        this.rentRepository = rentRepository;
        this.userService = userService;
        this.listingService = listingService;
        this.rentService = rentService;
    }

    /**
     * A method to save a new rating to the DB.
     * @param rating The RatingDAO of the new rating you want to store in the DB.
     */
    public void saveRating(RatingDAO rating) {
        System.out.println("New rating: '" + rating.getScore() + ", " + rating.getComment());
        ratingRepository.save(rating);
    }

    public List<RatingDTO> findRatingsAsRenterByUserID(int userID){
        //finner rating som leier
        UserDAO userDAO = userService.findUserByUserId(userID);
        ArrayList<RatingDTO> ratings = new ArrayList<>();
        List<RentDAO> rented = rentService.findRentByUserID(userDAO);
        for (RentDAO rentDAO : rented) {
            System.out.println(rentDAO.toString());
            List<RatingDAO> ratingsByRent = ratingRepository.findByRentIDAndRenterIsReceiverOfRatingFalse(rentDAO);
            for (RatingDAO dao : ratingsByRent){
                RatingDTO dto = new RatingDTO();
                dto.setComment(dao.getComment());
                dto.setScore(dao.getScore());
                ratings.add(dto);
            }
        }
        return ratings.subList(0,ratings.size());
    }

    public List<RatingDTO> findRatingsAsOwnerByUserID(int userID){
        //finner rating som utleier
        UserDAO userDAO = userService.findUserByUserId(userID);
        ArrayList<RatingDTO> ratings = new ArrayList<>();
        List<ListingDAO> listings = listingService.findListingsByUserDAO(userDAO);
        for (ListingDAO listingDAO : listings){
            List<RentDAO> rents = rentRepository.findRentDAOSByListingOwnerID(listingDAO);
            for (RentDAO rentDAO : rents){
                List<RatingDAO> ratingsByRent = ratingRepository.findByRentIDAndRenterIsReceiverOfRatingTrue(rentDAO);
                for (RatingDAO dao : ratingsByRent){
                    RatingDTO dto = new RatingDTO();
                    dto.setComment(dao.getComment());
                    dto.setScore(dao.getScore());
                    ratings.add(dto);
                }
            }
        }
        return ratings.subList(0,ratings.size());
    }
}
