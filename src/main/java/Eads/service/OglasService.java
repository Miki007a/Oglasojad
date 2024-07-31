package Eads.service;

import Eads.model.Oglas;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OglasService {


    List<Oglas> listAllProducts();


    Oglas findById(Long id);


    Oglas create(String name, Double price, LocalDate date, List<Long> categories, String description, String city, String user,String imagepath);

    Oglas update(Long id, String name, Double price, LocalDate date, List<Long> categories,String city);
    Oglas delete(Long id);
    Page<Oglas> listProductsByNameAndCategory(String name, Long categoryId,String city, Pageable pageable);
    Page<Oglas> findAllProducts(Pageable pageable);
    Page<Oglas> findAllByUser(String user,Pageable pageable);


}
