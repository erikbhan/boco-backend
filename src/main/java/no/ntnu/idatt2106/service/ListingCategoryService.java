package no.ntnu.idatt2106.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;
import no.ntnu.idatt2106.model.DAO.ListingCategoryDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.repository.ListingCategoryRepository;

/**
 * Service class for handling the junction between Listing and Category
 */
@Service
public class ListingCategoryService {
    @Autowired
    private final ListingCategoryRepository listingCategoryRepository;

    public ListingCategoryService(ListingCategoryRepository listingCategoryRepository) {
        this.listingCategoryRepository = listingCategoryRepository;
    }

    /**
     * Save a ListingCategoryDAO
     * @param listingCategoryDAO ListingCategoryDAO to save
     */
    public void save(ListingCategoryDAO listingCategoryDAO) {
        listingCategoryRepository.save(listingCategoryDAO);
    }

    /**
     * Creates a LisitingCategoryDAO with a CategoryDAO and ListingDAO, and saves it
     * to the junction table.
     * @param categoryDAO
     * @param listingDAO
     */
    public void saveListingCategory(CategoryDAO categoryDAO, ListingDAO listingDAO) {
        ListingCategoryDAO listingCategoryDAO = new ListingCategoryDAO();
        listingCategoryDAO.setCategory(categoryDAO);
        listingCategoryDAO.setListing(listingDAO);
        listingCategoryRepository.save(listingCategoryDAO);
    }

    /**
     * Gets a list of all the category names of the cateogories related to a listing
     * @param listingDAO
     * @return List fo category names
     */
    public String[] getCategoryNamesByListingID(ListingDAO listingDAO) {
        // Finds all the ListingCategories of a listing
        List<ListingCategoryDAO> listingCategoryDAOs = listingCategoryRepository
                .findAllFromListingCategoryDAOByListingID(listingDAO);
        String[] categoryNames = new String[listingCategoryDAOs.size()];
        // Loops through alle the listingCategories adding the category names to hte
        // categoryNames array.
        for (int i = 0; i < categoryNames.length; i++) {
            //
            categoryNames[i] = listingCategoryDAOs.get(i).getCategory().getName();
        }
        return categoryNames;
    }
}
