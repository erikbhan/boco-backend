package no.ntnu.idatt2106.service;

import org.springframework.stereotype.Service;

import no.ntnu.idatt2106.model.DAO.CategoryDAO;
import no.ntnu.idatt2106.repository.CategoryRepository;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public CategoryDAO findCategoryDAOByName(String name){
        System.out.println("\n\n-------------" + "Fy faen:)" + "\n\n-----------------");
        return categoryRepository.findCategoryDAOByName(name);
    }
}
