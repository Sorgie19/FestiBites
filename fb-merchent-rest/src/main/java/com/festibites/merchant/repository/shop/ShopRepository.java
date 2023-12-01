package com.festibites.merchant.repository.shop;

import com.festibites.merchant.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    // Find shops by name
    List<Shop> findByNameContaining(String name);

    // Find shops by active status
    List<Shop> findByActive(boolean active);

    // Find shops by owner ID
    List<Shop> findByOwnerId(Long ownerId);

    // Find shops by a specific category name
    List<Shop> findByCategoriesName(String categoryName);

}
