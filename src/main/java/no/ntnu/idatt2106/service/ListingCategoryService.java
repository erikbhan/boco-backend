package no.ntnu.idatt2106.service;

import java.util.ArrayList;
import java.util.List;

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
    public void saveListingCategory(CategoryDAO categoryDAO, ListingDAO listingDAO){
        ListingCategoryDAO listingCategoryDAO = new ListingCategoryDAO();
        listingCategoryDAO.setCategoryID(categoryDAO);
        listingCategoryDAO.setListingID(listingDAO);
        listingCategoryRepository.save(listingCategoryDAO);
    }

    public String[] getCategoryNamesByListingID(ListingDAO listingDAO){
        List<ListingCategoryDAO> listingCategoryDAOs = 
        listingCategoryRepository.findAllFromListingCategoryDAOByListingID(listingDAO);
        String[] categoryDAOs = new String[listingCategoryDAOs.size()];
        for (int i = 0; i < categoryDAOs.length; i++) {
            categoryDAOs[i] = listingCategoryDAOs.get(i).getCategoryID().getName();
        }
        return categoryDAOs;
    }
}
