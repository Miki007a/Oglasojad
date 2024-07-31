package Eads.service.impl;

import Eads.model.exceptions.InvalidCategoryIdException;
import Eads.model.Category;
import Eads.repository.jpa.CategoryRepository;
import Eads.service.CategoryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new InvalidCategoryIdException(id));
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategories() {
        return null;
    }
}
