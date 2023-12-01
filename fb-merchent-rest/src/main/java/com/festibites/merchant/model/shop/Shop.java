package com.festibites.merchant.model.shop;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.festibites.merchant.model.shop.category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {
	
	@Column(name = "owner_id", nullable = false)
	private Long ownerId;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;
	
    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 45)
    private String email;
    
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Category> categories;

    @Column(nullable = false)
    private boolean active;
    
    
	
	public Shop(Shop shop) 
	{
		this.ownerId = shop.ownerId;
		this.name = shop.name;
		this.description = shop.description;
		this.phone = shop.phone;
		this.email = shop.email;
		this.categories = shop.categories;
		this.active = shop.active;
	}

	public Shop() {

	}
	

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long owner) {
		this.ownerId = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	
}