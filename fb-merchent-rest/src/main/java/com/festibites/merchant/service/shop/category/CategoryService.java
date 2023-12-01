package com.festibites.merchant.service.shop.category;

import com.festibites.merchant.authentication.AuthenticationUtility;
import com.festibites.merchant.exception.ShopNotFoundException;
import com.festibites.merchant.model.shop.Shop;
import com.festibites.merchant.model.shop.category.Category;
import com.festibites.merchant.repository.shop.category.CategoryRepository;
import com.festibites.merchant.service.shop.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
	private CategoryRepository categoryRepository;
    
    @Autowired
    private ShopService shopService;
    
	@Autowired
	private AuthenticationUtility authenticationUtility;
    

    public Category addCategory(Category category) {
        Long shopId = category.getShop().getId();
        Shop shop = shopService.getShopById(shopId)
                        .orElseThrow(() -> new ShopNotFoundException("Shop not found with id: " + shopId));
        category.setShop(shop); // Set the retrieved shop to the category
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getCategoriesByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    public List<Category> getCategoriesByShopId(Long shopId) {
        return categoryRepository.findByShopId(shopId);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

