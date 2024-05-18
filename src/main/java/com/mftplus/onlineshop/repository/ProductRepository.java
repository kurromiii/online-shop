package com.mftplus.onlineshop.repository;

import com.mftplus.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying
    @Query("update productEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Product> findAllByDeletedFalse();
}
