package com.festibites.merchant.controller.shop;

import com.festibites.merchant.authentication.AuthenticationUtility;
import com.festibites.merchant.model.shop.Shop;
import com.festibites.merchant.service.shop.ShopService;
import com.festibites.merchant.service.user.UserService;
import com.festibites.merchant.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

	@Autowired
    private ShopService shopService;
      
    @Autowired
    private UserService userService;
    
	@Autowired
	private AuthenticationUtility authenticationUtility;

    @PostMapping
    public Shop addShop(@RequestBody Shop shop, @RequestHeader("Authorization") String authToken) 
    {
    	JwtUtil jwtUtil = new JwtUtil();
    	String username = jwtUtil.extractClaim(authToken, Claims::getSubject);
    	Long ownerId = userService.getUserIdByEmail(username);
    	//User test = userService.getUserByEmail(username);
    	shop.setOwnerId(ownerId);
    	return shopService.addShop(new Shop(shop));
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        return shopService.getShopById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Shop> searchShopsByName(@RequestParam String name) {
        return shopService.searchShopsByName(name);
    }

    @GetMapping("/active")
    public List<Shop> getShopsByActiveStatus(@RequestParam boolean active) {
        return shopService.getShopsByActiveStatus(active);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Shop> getShopsByOwnerId(@PathVariable Long ownerId) {
        return shopService.getShopsByOwnerId(ownerId);
    }

    @GetMapping("/category/{categoryName}")
    public List<Shop> getShopsByCategoryName(@PathVariable String categoryName) {
        return shopService.getShopsByCategoryName(categoryName);
    }

    @PutMapping("/{id}")
    public Shop updateShop(@PathVariable Long id, @RequestBody Shop shop) throws AccessDeniedException {
        shop.setId(id);
        Optional<Shop> shopOpt = shopService.getShopById(id);

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (shopOpt.isPresent() && !shopOpt.get().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        return shopService.updateShop(shop);
    }

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id) throws AccessDeniedException {
    	Optional<Shop> shopOpt = shopService.getShopById(id);
    	
        if (!shopOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found with id: " + id);
        }

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (shopOpt.isPresent() && !shopOpt.get().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        shopService.deleteShop(id);
    }
}
