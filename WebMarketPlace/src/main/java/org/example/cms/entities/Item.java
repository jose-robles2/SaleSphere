package org.example.cms.entities;

import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
public class Item {
    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private String locationState;

    private Long price;

    private int stock;

    public Item(String id, String name, String description, String imageUrl, String locationState, Long price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.locationState = locationState;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
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

    public Long getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return getStock() == item.getStock() && Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription()) && Objects.equals(getImageUrl(), item.getImageUrl()) && Objects.equals(getLocationState(), item.getLocationState()) && Objects.equals(getPrice(), item.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getImageUrl(), getLocationState(), getPrice(), getStock());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", locationState='" + locationState + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
