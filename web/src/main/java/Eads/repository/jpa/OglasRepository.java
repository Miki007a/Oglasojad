package Eads.repository.jpa;


import Eads.model.Category;
import Eads.model.Oglas;
import Eads.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OglasRepository extends JpaRepository<Oglas, Long> {
    Page<Oglas> findAllByNameLike(String name,Pageable pageable);
    Page<Oglas> findAllByNameLikeAndCategoriesContaining(String name, Category category,Pageable pageable);

    Page<Oglas> findAllByNameLikeAndCategoriesContainingAndCity(String name,Category category,String city,Pageable pageable);

    Page<Oglas> findAllByNameLikeAndCity(String name,String city,Pageable pageable);

    Page<Oglas> findAllByCategoriesContainingAndCity(Category category,String city,Pageable pageable);
    Page<Oglas> findAllByCategoriesContaining(Category category,Pageable pageable);
    Page<Oglas> findAllByCity(String city,Pageable pageable);
   Page<Oglas> findAllByCreator(User user,Pageable pageable);

}   
