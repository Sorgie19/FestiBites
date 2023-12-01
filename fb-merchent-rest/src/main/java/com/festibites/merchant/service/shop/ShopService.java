package com.festibites.merchant.service.shop;

import com.festibites.merchant.authentication.AuthenticationUtility;
import com.festibites.merchant.model.shop.Shop;
import com.festibites.merchant.repository.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
	
	@Autowired
    private ShopRepository shopRepository;
	@Autowired
	private AuthenticationUtility authenticationUtility;


    public Shop addShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Optional<Shop> getShopById(Long id) throws AccessDeniedException {
        Optional<Shop> shopOpt = shopRepository.findById(id);

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (shopOpt.isPresent() && !shopOpt.get().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }

        return shopOpt;
    }

    public List<Shop> searchShopsByName(String name) {
        return shopRepository.findByNameContaining(name);
    }

    public List<Shop> getShopsByActiveStatus(boolean active) {
        return shopRepository.findByActive(active);
    }

    public List<Shop> getShopsByOwnerId(Long ownerId) {
        return shopRepository.findByOwnerId(ownerId);
    }

    public List<Shop> getShopsByCategoryName(String categoryName) {
        return shopRepository.findByCategoriesName(categoryName);
    }

    public Shop updateShop(Shop shop) {
    	Optional<Shop> shopOpt = shopRepository.findById(shop.getId());

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (shopOpt.isPresent() && !shopOpt.get().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        return shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        shopRepository.deleteById(id);
    }
    
    // Add more methods as required based on your business logic
}
