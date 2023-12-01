package com.festibites.merchant.controller.shop.item;

import com.festibites.merchant.authentication.AuthenticationUtility;
import com.festibites.merchant.model.shop.category.Category;
import com.festibites.merchant.model.shop.item.Item;
import com.festibites.merchant.service.shop.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shops/categories/items")
public class ItemController {

	@Autowired
    private ItemService itemService;
    
	@Autowired
	private AuthenticationUtility authenticationUtility;

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) throws AccessDeniedException {
    	item.setId(id);
    	Optional<Item> itemOpt = itemService.getItemById(id);
        
        if (!itemOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (itemOpt.isPresent() && !itemOpt.get().getCategory().getShop().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        return itemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) throws AccessDeniedException {
    	Optional<Item> itemOpt = itemService.getItemById(id);
        
        if (!itemOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (itemOpt.isPresent() && !itemOpt.get().getCategory().getShop().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        itemService.deleteItem(id);
    }
}
