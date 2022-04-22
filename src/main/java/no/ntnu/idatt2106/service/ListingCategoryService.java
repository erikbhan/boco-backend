package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.DAO.ListingCategoryDAO;
import no.ntnu.idatt2106.model.DTO.ListingCategoryDTO;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.ListingCategoryRepository;
import no.ntnu.idatt2106.repository.ListingRepository;

public class ListingCategoryService {
    private final ListingCategoryRepository listingCategoryRepository; 
    private final CategoryRepository categoryRepository;  
    private final ListingRepository listingRepository;

    public ListingCategoryService(ListingCategoryRepository listingCategoryRepository, CategoryRepository categoryRepository,
    ListingRepository listingRepository){
        this.listingCategoryRepository = listingCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.listingRepository = listingRepository;
    }

    public void saveListingCategory(String categoryName, int listingID){
        ListingCategoryDAO listingCategoryDAO = new ListingCategoryDAO();
        listingCategoryDAO.setCategoryID(categoryRepository.findCategoryDAOByName(categoryName));
        listingCategoryDAO.setListingID(listingRepository.getById(listingID));
        listingCategoryRepository.save(listingCategoryDAO);
    }
}
