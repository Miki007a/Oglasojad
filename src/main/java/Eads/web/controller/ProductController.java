package Eads.web.controller;

import Eads.model.Oglas;
import Eads.service.OglasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {


    public ProductController(OglasService productService) {
        this.productService = productService;
    }

    private OglasService productService;

    @GetMapping("/products")
    public String listProducts(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Oglas> productPage = productService.findAllProducts(PageRequest.of(page , 5));
        model.addAttribute("productPage", productPage);
        return "list";
    }
}
