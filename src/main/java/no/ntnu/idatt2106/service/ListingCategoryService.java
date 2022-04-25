package no.ntnu.idatt2106.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;
import no.ntnu.idatt2106.model.DAO.ListingCategoryDAO;
import no.ntnu.idatt2106.model.DAO.ListingDAO;
import no.ntnu.idatt2106.model.DTO.ListingCategoryDTO;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.ListingCategoryRepository;
import no.ntnu.idatt2106.repository.ListingRepository;

/**
 * Service class for handling the junction between Listing and Category
 */
@Service
@Transactional
public class ListingCategoryService {
    @Autowired
    private final ListingCategoryRepository listingCategoryRepository; 

    public ListingCategoryService(ListingCategoryRepository listingCategoryRepository){
        this.listingCategoryRepository = listingCategoryRepository;
    }
    public void save(ListingCategoryDAO listingCategoryDAO){
        listingCategoryRepository.save(listingCategoryDAO);
    }
    /**
     * Creates a LisitingCategoryDAO with a CategoryDAO and ListingDAO, and saves it to the junction table.  
     * @param categoryDAO
     * @param listingDAO
     */
    public boolean saveListingCategory(CategoryDAO categoryDAO, ListingDAO listingDAO){
        System.out.println("\n-----------------------1------------------\n");
        ListingCategoryDAO listingCategoryDAO = new ListingCategoryDAO();
        System.out.println("\n-----------------------2------------------\n");
        listingCategoryDAO.setCategoryID(categoryDAO);
        System.out.println("\n-----------------------3------------------\n");
        listingCategoryDAO.setListingID(listingDAO);
        System.out.println("\n-----------------------4------------------\n");
        listingCategoryRepository.save(listingCategoryDAO);
        System.out.println("\n-----------------------5------------------\n");
        return true;
    }
}
