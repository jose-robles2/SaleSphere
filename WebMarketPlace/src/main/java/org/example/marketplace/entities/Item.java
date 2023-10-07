package org.example.marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Item {

    private String name;

    private String description;

    private String imageUrl;

    private String locationState;

    private double price;

    @Id
    @GeneratedValue
    private Long id;

    private int stock;

    private int category;

    public Item() {}

    public Item(String name, String description, String imageUrl, String locationState, double price, int stock, int category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.locationState = locationState;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocationState() {
        return locationState;
    }

    public double getPrice() {
        return price;
    }

    public Long getId() { return id; }

    public int getStock() {
        return stock;
    }

    public int getCategory() { return category; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategory(int category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Double.compare(getPrice(), item.getPrice()) == 0 && getStock() == item.getStock() && getCategory() == item.getCategory() && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription()) && Objects.equals(getImageUrl(), item.getImageUrl()) && Objects.equals(getLocationState(), item.getLocationState()) && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getImageUrl(), getLocationState(), getPrice(), getId(), getStock(), getCategory());
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", locationState='" + locationState + '\'' +
                ", price=" + price +
                ", id=" + id +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }
}
