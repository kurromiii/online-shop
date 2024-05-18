package com.mftplus.onlineshop.service;

import com.mftplus.onlineshop.model.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void save(Product product);
    void edit(Product product);
    void remove(Product product);
    void removeById(Long id);
    @Transactional
    void logicalRemove(Long id);

    List<Product> fndAll();
    List<Product> fndAllByDeletedFalse();
    Optional<Product> findById(Long id);
}
