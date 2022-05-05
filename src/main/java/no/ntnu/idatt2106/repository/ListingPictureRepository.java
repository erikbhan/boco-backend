package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.DAO.ListingDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.idatt2106.model.DAO.ListingPictureDAO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingPictureRepository extends JpaRepository<ListingPictureDAO, Integer> {
    List<ListingPictureDAO> findAllByListing(ListingDAO listing);
    boolean existsByListingAndPicture(ListingDAO listing, String picture);
    ListingPictureDAO findByListingAndPicture(ListingDAO listing, String picture);
}
