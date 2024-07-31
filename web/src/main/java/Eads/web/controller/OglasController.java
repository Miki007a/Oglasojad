package Eads.web.controller;

import Eads.model.User;
import Eads.service.UserService;
import Eads.model.Oglas;
import Eads.service.CategoryService;
import Eads.service.OglasService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class OglasController {

    private final OglasService service;
    private final CategoryService categoryService;

    public OglasController(OglasService service, CategoryService categoryService, UserService userService) {
        this.service = service;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private final UserService userService;


    @GetMapping({"/", "/home"})
    public String showHome(Model model) {
        model.addAttribute("categories",categoryService.listAll());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }

        return "home";
    }


    @PostMapping("/oglas/list")
    public String showListByName(@RequestParam(required = false) String nameSearch,
                                 RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("nameSearch", nameSearch);

        return "redirect:/oglas/list/filter";
    }



    @GetMapping("/oglas/list/{id}")
    public String showListByCategory(@PathVariable(required = false) Long id,
                                     Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Oglas> products = null;
        products = this.service.listProductsByNameAndCategory(null,id,null,pageable);
        model.addAttribute("productPage", products);
        model.addAttribute("categories", this.categoryService.listAll());
        model.addAttribute("categoryId",id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }
        return "list";
    }
    @GetMapping("/oglas/list/city/{id}")
    public String showListByCity(@PathVariable(required = false)String id,
                                 Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Oglas> products = null;
        products = this.service.listProductsByNameAndCategory(null,null,id,pageable);
        model.addAttribute("productPage", products);
        model.addAttribute("categories", this.categoryService.listAll());
        model.addAttribute("city",id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }
        return "list";
    }

    @GetMapping("/oglas/list/filter")
    public String showListFilter(@RequestParam(required = false) String nameSearch,
                                 @RequestParam(required = false) String city,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(required = false) String mojoglas,
                                 Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<Oglas> products = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();

            User user = userService.findByUsername(currentUserName);

            model.addAttribute("username", user.getName()+" "+ user.getSurname());

            if(!(mojoglas==null ||  mojoglas.equals(""))){
                products=service.findAllByUser(currentUserName,pageable);
                model.addAttribute("mojoglas",mojoglas);
            }

        }
            if ((mojoglas == null || mojoglas.equals(""))){
                if ((nameSearch == null || nameSearch.equals("")) && (city == null || city.equals("")) && categoryId == null) {
                    products = service.findAllProducts(pageable);
                } else {
                    products = service.listProductsByNameAndCategory(nameSearch, categoryId, city, pageable);
                }
        }

        model.addAttribute("productPage", products);
        model.addAttribute("categories", this.categoryService.listAll());

            model.addAttribute("city",city);
           model.addAttribute("categoryId",categoryId);
           model.addAttribute("nameSearch",nameSearch);

        return "list";
    }



    @GetMapping({"/oglas/add"})
    public String showAdd(Model model,@RequestParam(required = false) String imagePath,
                          @RequestParam(required = false) String  imageSuccess,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String description,
                          @RequestParam(required = false) String price,
                          @RequestParam(required = false)String city,
                          @RequestParam(required = false) List<Long> categories) {


        model.addAttribute("categories", this.categoryService.listAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
             model.addAttribute("name",name);
             model.addAttribute("description",description);
             model.addAttribute("categoryId",categories);
             model.addAttribute("price",price);
             model.addAttribute("city",city);
            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }


        if(!(imagePath==null || imagePath.equals(""))){
            model.addAttribute("imageSuccess",imageSuccess);
            model.addAttribute("imagePath",imagePath);
        }
        return "form";
    }

    @GetMapping({"/oglas/{id}/edit"})
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.service.findById(id));
        model.addAttribute("categories", this.categoryService.listAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }
        return "form";
    }


    @PostMapping("/oglas/add")
    public String create(@RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam List<Long> categories,
                         @RequestParam String description,
                         @RequestParam String city,
                          @RequestParam(required = false) String imagePath) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String user = authentication.getName();

        this.service.create(name, price, LocalDate.now(), categories,description,city,user,imagePath);
        return "redirect:/oglas/list/filter";
    }

    @PostMapping({"oglas/{id}"})
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam List<Long> categories,
                         @RequestParam String city,
                         @RequestParam(required = false) String mojolgas,
                         RedirectAttributes redirectAttributes
                         ) {
        this.service.update(id, name, price, LocalDate.now(), categories,city);
        redirectAttributes.addAttribute("mojoglas",mojolgas);
        return "redirect:/oglas/list/filter";
    }

    @PostMapping({"/oglas/{id}/delete"})
    public String delete(@PathVariable Long id, @RequestParam(required = false) String mojoglas,RedirectAttributes redirectAttributes) {
        this.service.delete(id);
          redirectAttributes.addAttribute("mojoglas",mojoglas);

        return "redirect:/oglas/list/filter";
    }
    @GetMapping({"/moj/oglasi"})
    public String ShowMyAds(RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("mojoglas","mojoglas");

        return "redirect:/oglas/list/filter";
    }
    @GetMapping({"/oglas/pokazhi/{id}"})
    public String ShowAd(@PathVariable Long id,Model model){
        model.addAttribute("product",service.findById(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {

            String currentUserName = authentication.getName();
            User user = userService.findByUsername(currentUserName);
            model.addAttribute("username", user.getName()+" "+ user.getSurname());

        }
        return "Product";
    }
}
