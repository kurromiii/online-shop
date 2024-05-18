package com.mftplus.onlineshop.service;

import com.mftplus.onlineshop.model.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void save(Category category);
    void edit(Category category);
    void remove(Category category);
    void removeById(Long id);
    @Transactional
    void logicalRemove(Long id);

    List<Category> fndAll();
    List<Category> fndAllByDeletedFalse();
    Optional<Category> findById(Long id);
}
