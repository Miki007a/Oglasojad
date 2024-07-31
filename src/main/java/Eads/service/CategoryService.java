package Eads.service;

import Eads.model.Category;

import java.util.List;

/**
 * 5 points
 */
public interface CategoryService {

    Category findById(Long id);

    List<Category> listAll();

    Category create(String name);

    List<Category> listCategories();
}
