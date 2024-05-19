package com.mftplus.onlineshop.controller;

import com.mftplus.onlineshop.service.Impl.CategoryServiceImpl;
import com.mftplus.onlineshop.service.Impl.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private final CategoryServiceImpl categoryService;
    private final ProductServiceImpl productService;

    public HomeController(CategoryServiceImpl categoryService, ProductServiceImpl productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model){
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.findAllByDeletedFalse());
        model.addAttribute("products", productService.findAllByDeletedFalse());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable Long id){
        model.addAttribute("categories", categoryService.findAllByDeletedFalse());
        model.addAttribute("products", productService.findAllByCategoryIdAndDeletedFalse(id));
        return "shop";
    }

    @GetMapping("/shop/viewProduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.findById(id).get());
        return "viewProduct";
    }
}
