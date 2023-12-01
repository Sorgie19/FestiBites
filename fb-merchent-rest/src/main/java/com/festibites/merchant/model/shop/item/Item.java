package com.festibites.merchant.model.shop.item;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.festibites.merchant.model.shop.category.Category;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean available;

    // Assuming each item belongs to a category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    // Constructors, getters, and setters

    public Item() {
        // Default constructor
    }

    public Item(Long id, String name, String description, double price, boolean available, Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.category = category;
	}
    
    public Item(Item item) {
		this.id = item.id;
		this.name = item.name;
		this.description = item.description;
		this.price = item.price;
		this.available = item.available;
		this.category = item.category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
