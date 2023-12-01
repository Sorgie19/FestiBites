package com.festibites.merchant.repository.shop.item;

import com.festibites.merchant.model.shop.item.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByNameContaining(String name);
	List<Item> findByAvailable(boolean available);
	List<Item> findByPriceBetween(double minPrice, double maxPrice);
	List<Item> findByCategoryName(String categoryName);
}