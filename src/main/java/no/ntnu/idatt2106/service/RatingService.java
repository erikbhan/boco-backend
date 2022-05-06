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
        ratingRepository.save(rating);
    }

    /**
     * A method to find all ratings of a user as the renter
     * @param userID The userID of the user we want to find the ratings of
     * @return A list of ratingDTOs where the user is the renter
     */
    public List<RatingDTO> findRatingsAsRenterByUserID(int userID) {
        UserDAO userDAO = userService.findUserByUserId(userID);
        ArrayList<RatingDTO> ratings = new ArrayList<>();
        List<RentDAO> rented = rentService.findRentByRenterID(userDAO);
        for (RentDAO rentDAO : rented) {
            List<RatingDAO> ratingsByRent = ratingRepository.findByRentAndRenterIsReceiverOfRatingTrue(rentDAO);
            for (RatingDAO dao : ratingsByRent) {
                RatingDTO dto = new RatingDTO();
                dto.setComment(dao.getComment());
                dto.setScore(dao.getScore());
                ratings.add(dto);
            }
        }
        return ratings.subList(0, ratings.size());
    }

    /**
     * A method to find all ratings of a user as the owner of the listing
     * @param userID The user we want to find the ratings of
     * @return A list of ratingDTOs where the user is the owner
     */
    public List<RatingDTO> findRatingsAsOwnerByUserID(int userID) {
        UserDAO userDAO = userService.findUserByUserId(userID);
        ArrayList<RatingDTO> ratings = new ArrayList<>();
        List<ListingDAO> listings = listingService.findListingsByUserDAO(userDAO);
        for (ListingDAO listingDAO : listings) {
            List<RentDAO> rents = rentRepository.findRentDAOSByListing(listingDAO);
            for (RentDAO rentDAO : rents) {
                List<RatingDAO> ratingsByRent = ratingRepository.findByRentAndRenterIsReceiverOfRatingFalse(rentDAO);
                for (RatingDAO dao : ratingsByRent) {
                    RatingDTO dto = new RatingDTO();
                    dto.setComment(dao.getComment());
                    dto.setScore(dao.getScore());
                    ratings.add(dto);
                }
            }
        }
        return ratings.subList(0, ratings.size());
    }

    /**
     * Finds all ratings for a Rent by providing RentID
     * @param rentID ID for rent to find ratings
     * @return A list of all ratings for a Rent
     */
    public List<RatingDAO> findRatingsByRentID(int rentID) {
        RentDAO rentDAO = rentService.getRentFromId(rentID);
        return ratingRepository.findByRent(rentDAO);
    }

    /**
     * Checks if a user has given a rating
     * @param userDAO
     * @param rentID
     * @return
     */
    public boolean userHasGivenRating(UserDAO userDAO, int rentID) {
        List<RatingDAO> ratings = findRatingsByRentID(rentID);
        RentDAO rent = rentRepository.findByRentID(rentID);

        //If user is not a part of rent or listing return false
        if (!rent.getRenter().equals(userDAO) && !rent.getListing().getUser().equals(userDAO)) {
            return false;
        }

        for (RatingDAO rating : ratings) {
            if ((rating.isRenterIsReceiverOfRating() && !rent.getRenter().equals(userDAO)) ||
                    (!rating.isRenterIsReceiverOfRating() && rent.getRenter().equals(userDAO))) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to find the average rating of the user with the given userID
     * @param userID The user we want to find the rating of
     * @return The average rating of all the users ratings
     */
    public float findAverageRating(int userID) {
        float averageRating = 0;
        List<RatingDTO> ratingsAsRenter = findRatingsAsRenterByUserID(userID);
        List<RatingDTO> ratingAsOwner = findRatingsAsOwnerByUserID(userID);
        for (RatingDTO asRenter : ratingsAsRenter) {
            averageRating += asRenter.getScore();
        }
        for (RatingDTO asOwner : ratingAsOwner) {
            averageRating += asOwner.getScore();
        }
        averageRating /= (ratingAsOwner.size() + ratingsAsRenter.size());
        return averageRating;
    }

    /**
     * Finds a users average rating as owner
     * @param userID user to find rating for
     * @return a float containing the average rating of the user as owner
     */
    public float findAverageRatingAsOwner(int userID) {
        float averageRating = 0;
        List<RatingDTO> ratingAsOwner = findRatingsAsOwnerByUserID(userID);
        for (RatingDTO asOwner : ratingAsOwner) {
            averageRating += asOwner.getScore();
        }
        averageRating /= ratingAsOwner.size();
        return averageRating;
    }

    /**
     * Finds a users average rating as renter
     * @param userID user to find rating for
     * @return a float containing the average rating of the user as renter
     */
    public float findAverageRatingAsRenter(int userID) {
        float averageRating = 0;
        List<RatingDTO> ratingsAsRenter = findRatingsAsRenterByUserID(userID);
        for (RatingDTO asRenter : ratingsAsRenter) {
            averageRating += asRenter.getScore();
        }
        averageRating /= ratingsAsRenter.size();
        return averageRating;
    }
}
