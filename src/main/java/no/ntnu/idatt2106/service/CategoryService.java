package no.ntnu.idatt2106.service;

import org.springframework.stereotype.Service;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;
import no.ntnu.idatt2106.repository.CategoryRepository;
@Service
/**
 * Service class for the categories
 */
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    /**
     * Finds a CategoryDAO by its name
     * @param name
     * @return
     */
    public CategoryDAO findCategoryDAOByName(String name){
        return categoryRepository.findCategoryDAOByName(name);
    }

    /**
     * Add a CategoryDAO
     * @param category CategoryDAO to add
     */
    public void addCategory(CategoryDAO category){categoryRepository.save(category);}
}
