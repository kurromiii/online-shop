package com.mftplus.onlineshop.service.Impl;

import com.mftplus.onlineshop.model.Product;
import com.mftplus.onlineshop.repository.ProductRepository;
import com.mftplus.onlineshop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void edit(Product product) {

    }

    @Override
    public void remove(Product product) {

    }

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void logicalRemove(Long id) {
        productRepository.logicalRemove(id);
    }

    @Override
    public List<Product> fndAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> fndAllByDeletedFalse() {
        return productRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Product> findById(Long Id) {
        return productRepository.findById(Id);
    }
}
