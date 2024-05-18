package com.mftplus.onlineshop.service.Impl;

import com.mftplus.onlineshop.model.Category;
import com.mftplus.onlineshop.repository.CategoryRepository;
import com.mftplus.onlineshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void edit(Category category) {

    }

    @Override
    public void remove(Category category) {

    }

    @Override
    public void removeById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void logicalRemove(Long id) {
        categoryRepository.logicalRemove(id);
    }

    @Override
    public List<Category> fndAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> fndAllByDeletedFalse() {
        return categoryRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}
