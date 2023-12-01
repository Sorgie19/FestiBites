package com.festibites.merchant.controller.shop.category;

import com.festibites.merchant.authentication.AuthenticationUtility;
import com.festibites.merchant.model.shop.category.Category;
import com.festibites.merchant.service.shop.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shops/categories")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;
    
	@Autowired
	private AuthenticationUtility authenticationUtility;

    @PostMapping
    public Category addCategory(@RequestBody Category category)
    {    	
        return categoryService.addCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Category> searchCategoriesByName(@RequestParam String name) {
        return categoryService.searchCategoriesByName(name);
    }

    @GetMapping("/shop/{shopId}")
    public List<Category> getCategoriesByShopId(@PathVariable Long shopId) {
        return categoryService.getCategoriesByShopId(shopId);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) throws AccessDeniedException {
        category.setId(id); // Ensure the category's ID is set to the path variable
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        
        if (!categoryOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (categoryOpt.isPresent() && !categoryOpt.get().getShop().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) throws AccessDeniedException {
    	Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        
        if (!categoryOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }

        Long currentUserId = authenticationUtility.getCurrentAuthenticatedUserId();
        if (categoryOpt.isPresent() && !categoryOpt.get().getShop().getOwnerId().equals(currentUserId)) {
            throw new AccessDeniedException("You do not have permission to access this shop.");
        }
        categoryService.deleteCategory(id);
    }
}
