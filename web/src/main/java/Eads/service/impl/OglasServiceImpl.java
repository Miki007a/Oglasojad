package Eads.service.impl;

import Eads.model.Category;
import Eads.model.Oglas;
import Eads.model.User;
import Eads.model.exceptions.InvalidCategoryIdException;
import Eads.model.exceptions.InvalidOglasIdException;
import Eads.repository.jpa.CategoryRepository;
import Eads.repository.jpa.OglasRepository;
import Eads.repository.jpa.UserRepository;
import Eads.service.OglasService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class OglasServiceImpl implements OglasService {

    private final OglasRepository oglasRepository;
    private final CategoryRepository categoryRepository;



    public OglasServiceImpl(OglasRepository oglasRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.oglasRepository = oglasRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    private final UserRepository userRepository;


    @Override
    public List<Oglas> listAllProducts() {
        return oglasRepository.findAll();
    }

    @Override
    public Oglas findById(Long id) {
        return oglasRepository.findById(id).orElseThrow(()->new InvalidOglasIdException(id));
    }

    @Override
    public Oglas create(String name, Double price, LocalDate date, List<Long> categories, String description, String city, String user,String imagepath) {
           User user1=userRepository.findByUsername(user).orElseThrow(()->new RuntimeException());
        List<Category> categories_obj = categoryRepository.findAllById(categories);
        Oglas oglas = new Oglas(name, price, date, categories_obj,description,city,user1,imagepath);
        return oglasRepository.save(oglas);
    }

    @Override
    public Oglas update(Long id, String name, Double price, LocalDate date, List<Long> categories,String city) {

        Oglas oglas = oglasRepository.findById(id).orElseThrow(()->new InvalidOglasIdException(id));
        List<Category> categories_obj = categoryRepository.findAllById(categories);

        oglas.setName(name);
        oglas.setPrice(price);
        oglas.setDate(date);
        oglas.setCategories(categories_obj);
        oglas.setCity(city);
        return oglasRepository.save(oglas);
    }


    @Override
    public Oglas delete(Long id) {
        Oglas oglas = oglasRepository.findById(id).orElseThrow(()->new InvalidOglasIdException(id));
        oglasRepository.delete(oglas);
        return oglas;
    }

    @Override
    public Page<Oglas> listProductsByNameAndCategory(String name, Long categoryId, String city,Pageable pageable) {
        String nameSearch = name != null && !name.equals("") ? '%' + name + '%' : null;

        if (nameSearch == null && categoryId == null && (city == null || city.equals(""))) {

            return findAllProducts(pageable);
        }
        if (nameSearch != null && categoryId != null && (city != null && !city.equals(""))) {

            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
            return oglasRepository.findAllByNameLikeAndCategoriesContainingAndCity(nameSearch, category, city,pageable);
        } else if (nameSearch != null && categoryId != null) {

            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
            return oglasRepository.findAllByNameLikeAndCategoriesContaining(nameSearch, category,pageable);
        } else if (nameSearch != null && (city != null && !city.equals(""))) {

            return oglasRepository.findAllByNameLikeAndCity(nameSearch, city,pageable);
        } else if (categoryId != null && (city != null && !city.equals(""))) {

            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
            return oglasRepository.findAllByCategoriesContainingAndCity(category, city,pageable);
        } else if (categoryId != null) {

            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
            return oglasRepository.findAllByCategoriesContaining(category,pageable);
        } else if (nameSearch != null) {

            return oglasRepository.findAllByNameLike(nameSearch,pageable);
        } else if (city != null && !city.equals("")) {

            return oglasRepository.findAllByCity(city,pageable);
        }
        return Page.empty();
    }

    @Override
    public Page<Oglas> findAllProducts(Pageable pageable) {
       return oglasRepository.findAll(pageable);
    }

    @Override
    public Page<Oglas> findAllByUser(String user, Pageable pageable) {
        User user1=userRepository.findByUsername(user).orElseThrow(()->new RuntimeException());
        return oglasRepository.findAllByCreator(user1,pageable);
    }

}
