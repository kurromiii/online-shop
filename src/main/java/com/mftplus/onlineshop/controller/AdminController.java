package com.mftplus.onlineshop.controller;

import com.mftplus.onlineshop.dto.ProductDTO;
import com.mftplus.onlineshop.model.Category;
import com.mftplus.onlineshop.model.Product;
import com.mftplus.onlineshop.service.Impl.CategoryServiceImpl;
import com.mftplus.onlineshop.service.Impl.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

//    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    public static String uploadDir = "src/main/resources/static/productImages";

    private final CategoryServiceImpl categoryService;
    private final ProductServiceImpl productService;

    public AdminController(CategoryServiceImpl categoryService, ProductServiceImpl productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.findAllByDeletedFalse());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable Long id){
        categoryService.logicalRemove(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable Long id, Model model){
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else
            return "404";
    }

    //product section
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findAllByDeletedFalse());
        return "products";
    }
    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.findAllByDeletedFalse());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile file,
                                  @RequestParam(value = "imageName",required = false) String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        if (imgName == null){
            imgName = productDTO.getImageName();
        }
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir , imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.save(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.logicalRemove(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable Long id, Model model){
        Product product = productService.findById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.findAllByDeletedFalse());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }

}
