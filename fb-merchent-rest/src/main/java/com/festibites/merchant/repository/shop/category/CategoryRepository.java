package com.festibites.merchant.repository.shop.category;

import com.festibites.merchant.model.shop.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find categories by name (exact match)
    List<Category> findByName(String name);

    // Find categories by name containing a substring (like search)
    List<Category> findByNameContaining(String name);

    // Find categories by shop id
    List<Category> findByShopId(Long shopId);

    // Additional custom queries can be added here
}